package com.haku.excalibur.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.haku.excalibur.Excalibur

/**
 * Usage:
 *
 * Created by HaKu on 2019-06-10.
 */
object AMSUtils {

    fun startActivity(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        Excalibur.instance.startActivity(intent)
    }
}