package com.haku.excalibur.config

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.haku.excalibur.model.ScriptItem
import org.jetbrains.anko.*


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-25.
 */
class ConfigItemView(context: Context) : FrameLayout(context) {

    private lateinit var mTvName: TextView
    private lateinit var mTvContent: TextView

    private lateinit var mBtnEdit: Button
    private lateinit var mBtnUse: Button
    private lateinit var mBtnDelete: Button

    private val size = 20f

    private var mRootView = with(context) {
        verticalLayout {
            mTvName = textView {
                textSize = size
            }.lparams(matchParent, wrapContent)
            mTvContent = textView {
                textSize = size
            }.lparams(matchParent, wrapContent)
            linearLayout {
                mBtnEdit = button("编辑") {

                }.lparams(0, wrapContent, 1.0f)
                mBtnUse = button("使用") {

                }.lparams(0, wrapContent, 1.0f)
                mBtnDelete = button("删除") {

                }.lparams(0, wrapContent, 1.0f)
            }
        }
    }

    init {
        mRootView.layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
        this.addView(mRootView)
    }

    @SuppressLint("SetTextI18n")
    fun setData(item: ScriptItem) {
        mTvName.text = "名称：${item.name}"
        mTvContent.text = item.content
    }

    fun setOnClick(edit: () -> Unit, use: () -> Unit, delete: () -> Unit) {
        mBtnDelete.setOnClickListener {
            delete()
        }
        mBtnEdit.setOnClickListener {
            edit()
        }
        mBtnUse.setOnClickListener {
            use()
        }
    }
}