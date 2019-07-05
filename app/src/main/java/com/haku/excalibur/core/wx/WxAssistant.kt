package com.haku.excalibur.core.wx

import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.haku.excalibur.core.Assistant
import com.haku.excalibur.utils.ADBUtils
import com.haku.excalibur.utils.SpUtils
import java.util.*


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-26.
 */
object WxAssistant : Assistant {

    private var tap = 0

    override fun handleEvent(event: AccessibilityEvent) {
        Log.e("HaKu", "event: ${event.className} && ${event.eventType}")

        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            if (tapRedPacket(event.source)) {
                tap = 20
            }
        }

        if (tap > 0) {
            openRedPacket(event.source)
            tap--
        }

        when {
            event.className.contains("LuckyMoneyDetailUI") -> {
                tap = 0
            }
            else -> {

            }
        }
    }

    override fun start() {
    }

    private fun tapRedPacket(node: AccessibilityNodeInfo): Boolean {
        Log.e("HaKu", "开始查询 微信红包")
        val bounds = getBounds(node, arrayOf("已领取", "已被领完"))
        return tapText(node, "微信红包", 100, -100, bounds)
    }

    private fun openRedPacket(node: AccessibilityNodeInfo) {
        Log.e("HaKu", "打开红包 ")
        val item = SpUtils.scripts.items.first { it.name == "微信红包" }
        ADBUtils.exec("input tap ${item.content.split(',')[0]} ${item.content.split(',')[1]}")
    }

    private fun getBounds(node: AccessibilityNodeInfo, str: Array<String>): HashSet<Rect> {
        val ans = hashSetOf<Rect>()
        val queue: Queue<AccessibilityNodeInfo> = LinkedList()
        queue.add(node)
        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 until size) {
                val item = queue.poll() ?: continue
                if (item.className.contains("TextView")) {
                    Log.e("HaKu", "Text: ${item.text}")
                    if (item.text != null && str.any {
                            item.text.contains(it)
                        }) {
                        val bounds = Rect()
                        item.getBoundsInScreen(bounds)
                        ans.add(bounds)
                    }
                }
                for (j in 0 until item.childCount) {
                    queue.offer(item.getChild(j))
                }
            }
        }
        return ans
    }

    private fun tapText(
        node: AccessibilityNodeInfo, str: String, offsetX: Int = 0, offsetY: Int = 0,
        bounds: HashSet<Rect>
    ): Boolean {
        val queue: Queue<AccessibilityNodeInfo> = LinkedList()
        queue.add(node)
        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 until size) {
                val item = queue.poll() ?: continue
                Log.e("HaKu", "${item.className}")
                if (item.className.contains("TextView")) {
                    Log.e("HaKu", "Text: ${item.text}")
                    if (item.text != null && item.text.contains(str)) {
                        val rect = Rect()
                        item.getBoundsInScreen(rect)
                        if (!hasOpened(bounds, rect)) {
                            Log.e("HaKu", "bounds: $rect")
                            ADBUtils.exec("input tap ${rect.left + offsetX} ${rect.bottom + offsetY}")
                            return true
                        }
                    }
                }
                for (j in 0 until item.childCount) {
                    queue.offer(item.getChild(j))
                }
            }
        }
        Log.e("HaKu", "未找到: $str")
        return false
    }

    private fun hasOpened(bounds: HashSet<Rect>, rect: Rect): Boolean {
        Log.e("HaKu", "start")
        Log.e("HaKu rect", "$rect")
        bounds.forEach {
            Log.e("HaKu", "$it")
            if (rect.centerY() - it.centerY() < 150) {
                Log.e("HaKu", "已打开")
                return true
            }
        }
        Log.e("HaKu", "未打开")
        return false
    }
}