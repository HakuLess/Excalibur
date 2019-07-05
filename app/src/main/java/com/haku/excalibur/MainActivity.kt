package com.haku.excalibur

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.haku.excalibur.config.ConfigActivity
import com.haku.excalibur.core.SwordService
import com.haku.excalibur.utils.AccessibilityUtils
import org.jetbrains.anko.button
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {

    companion object {
        private const val CHECK_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            button("选择脚本") {
                setOnClickListener {
                    val intent = Intent(context, ConfigActivity::class.java)
                    startActivity(intent)
                }
            }

            button("开启服务") {
                setOnClickListener {
                    if (AccessibilityUtils.checkAccessibility(context)) {
                        startService(
                            Intent(applicationContext, SwordService::class.java)
                                .putExtra(SwordService.COMMAND, SwordService.OPEN)
                        )
                    }
                }
            }
        }

        checkOverlayPermission()
    }

    // 检查是否有悬浮窗权限
    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:$packageName")
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), CHECK_REQUEST_CODE
                )
                Toast.makeText(this, "请先授予权限", Toast.LENGTH_LONG).show()
            }
        }
    }
}
