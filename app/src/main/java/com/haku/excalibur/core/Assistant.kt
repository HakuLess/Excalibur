package com.haku.excalibur.core

import android.view.accessibility.AccessibilityEvent


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-10.
 */
interface Assistant {

    fun start()

    fun handleEvent(event: AccessibilityEvent)
}