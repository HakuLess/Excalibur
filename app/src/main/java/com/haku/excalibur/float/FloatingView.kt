package com.haku.excalibur.float

import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import com.haku.excalibur.R
import com.haku.excalibur.core.AssState
import com.haku.excalibur.utils.ADBUtils
import org.jetbrains.anko.dip
import org.jetbrains.anko.imageView
import org.jetbrains.anko.verticalLayout
import java.lang.Math.abs
import kotlin.concurrent.thread


/**
 * Usage: 浮动View
 *
 * Created by HaKu on 2019-06-10.
 */
class FloatingView(context: Context) : LinearLayout(context) {

    private val mWindowManager: WindowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    // 点击范围判定
    private val mLimit = 50

    // 悬浮球宽高
    private val mHeight = 40
    private val mWidth = 40

    private val mPre = Point()
    private val mCur = Point()

    private lateinit var mImageView: ImageView
    private lateinit var mCurThread: Thread

    init {
        initView()
    }

    private fun initView() {
        verticalLayout {
            mImageView = imageView {
                setImageResource(R.drawable.start)
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }.lparams(width = dip(mWidth), height = dip(mHeight)) {

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mPre.set(event.rawX.toInt(), event.rawY.toInt())
                Log.e("HaKu", "Action Down: ")
            }

            // 拖拽移动
            MotionEvent.ACTION_MOVE -> {
                Log.e("HaKu", "Action Move: ${event.rawX} ${event.rawY}")
                val layoutParams = this.layoutParams as WindowManager.LayoutParams
                layoutParams.x = event.rawX.toInt() - mWidth * 2
                layoutParams.y = event.rawY.toInt() - mHeight * 2
                mWindowManager.updateViewLayout(this, layoutParams)
            }

            MotionEvent.ACTION_UP -> {
                mCur.set(event.rawX.toInt(), event.rawY.toInt())
                if (abs(mCur.x - mPre.x) <= mLimit && abs(mCur.y - mPre.y) <= mLimit) {
                    Log.e("HaKu", "Click: ")
                    AssState.isRunning = if (AssState.isRunning) {
                        mImageView.setImageResource(R.drawable.start)
                        false
                    } else {
                        mImageView.setImageResource(R.drawable.stop)
                        if (ADBUtils.curCmd.content.isNotEmpty()) {
                            mCurThread = thread {
                                while (AssState.isRunning) {
                                    Log.e("HaKu", "${mCurThread.id}")
                                    ADBUtils.exec(ADBUtils.curCmd.content)
                                    if (ADBUtils.curCmd.gap.isNotBlank()) {
                                        Thread.sleep(ADBUtils.curCmd.gap.toLong())
                                    }
                                }
                            }
                        }
                        true
                    }
                }
                Log.e("HaKu", "Action Up: ")
            }
        }

        return false
    }

}