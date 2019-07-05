package com.haku.excalibur.core

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.haku.excalibur.core.wx.WxAssistant
import com.haku.excalibur.core.wx.WxConstants
import com.haku.excalibur.float.FloatWindowManager


/**
 * Usage: 辅助功能，监听用户事件
 *
 * Created by HaKu on 2019-06-10.
 */
class SwordService : AccessibilityService() {

    private var mWindowManger: FloatWindowManager? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initWindowManager()

        if (intent == null) {
            return super.onStartCommand(null, flags, startId)
        }

        val command = intent.getStringExtra(COMMAND)
        if (command != null) {
            if (command == OPEN) {
                mWindowManger!!.addView()
            } else if (command == CLOSE) {
                mWindowManger!!.removeView()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.e("HaKu", "AccessibilityService has been connected!")
    }

    override fun onInterrupt() {
        Log.e("HaKu", "AccessibilityService has been interrupted!")
    }

    override fun onDestroy() {
        Log.e("HaKu", "AccessibilityService has been destroyed!")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.packageName != null && mWindowManger != null
            && AssState.isRunning
            && event.eventType != AccessibilityEvent.TYPE_VIEW_CLICKED
        ) {
            val source = event.source ?: return
            Log.e("HaKu", "pkgName: ${source.packageName}")
            when (source.packageName) {
                WxConstants.pkgName -> {
                    WxAssistant.handleEvent(event)
                }
                else -> {

                }
            }
        }
    }

    private fun initWindowManager() {
        if (mWindowManger == null) {
            mWindowManger = FloatWindowManager(this)
        }
    }

    companion object {
        const val COMMAND = "COMMAND"
        const val OPEN = "OPEN"
        const val CLOSE = "CLOSE"
    }
}