package com.haku.excalibur.config

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haku.excalibur.model.Scripts
import com.haku.excalibur.model.ScriptItem
import com.haku.excalibur.utils.SpUtils
import org.jetbrains.anko.*


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-25.
 */
class EditActivity : AppCompatActivity() {

    private lateinit var mNameEdit: EditText
    private lateinit var mContentEdit: EditText
    private lateinit var mGapEdit: EditText
    private var mPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            mNameEdit = editText {
                hint = "名称"
            }.lparams(matchParent, wrapContent)

            mContentEdit = editText {
                hint = "脚本内容"
            }.lparams(matchParent, wrapContent)

            mGapEdit = editText {
                hint = "间隔时间：2000"
            }.lparams(matchParent, wrapContent)

            button("保存") {
                setOnClickListener {
                    save()
                }
            }.lparams(matchParent, wrapContent)
        }

        mPos = intent.getIntExtra("pos", -1)
        if (mPos != -1) {
            mNameEdit.setText(SpUtils.scripts.items[mPos].name)
            mContentEdit.setText(SpUtils.scripts.items[mPos].content)
            mGapEdit.setText(SpUtils.scripts.items[mPos].gap)
        }
    }

    private fun save() {
        val name = mNameEdit.text.toString()
        val content = mContentEdit.text.toString()
        val gap = mGapEdit.text.toString()
        val item = ScriptItem(name, content, gap)
        val script = SpUtils.scripts
        if (mPos == -1) {
            script.items.add(item)
        } else {
            script.items[mPos] = item
        }
        SpUtils.scripts = Scripts(script.items)

        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
    }
}