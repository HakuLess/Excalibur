package com.haku.excalibur.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-10.
 */
object AccessibilityUtils {

    fun checkAccessibility(context: Context): Boolean {
        // 判断辅助功能是否开启
        if (!isAccessibilitySettingsOn(context)) {
            // 引导至辅助功能设置页面
            context.startActivity(
                Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            Toast.makeText(context, "请先开启 相应 辅助功能", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun isAccessibilitySettingsOn(context: Context): Boolean {
        var accessibilityEnabled = 0
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }

        if (accessibilityEnabled == 1) {
            val services = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (services != null) {
                return services.toLowerCase().contains(context.packageName.toLowerCase())
            }
        }

        return false
    }
}