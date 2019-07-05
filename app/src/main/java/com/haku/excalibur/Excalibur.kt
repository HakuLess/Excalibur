package com.haku.excalibur

import android.app.Application
import com.haku.excalibur.utils.SpUtils


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-10.
 */
class Excalibur : Application() {
    companion object {
        lateinit var instance: Excalibur
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        SpUtils.init(this)
    }
}