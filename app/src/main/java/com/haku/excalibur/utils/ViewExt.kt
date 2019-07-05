package com.haku.excalibur.utils

import android.app.Activity
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.custom.ankoView


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-24.
 */

inline fun Activity.recyclerView() = recyclerView {}

inline fun Activity.recyclerView(init: RecyclerView.() -> Unit): RecyclerView {
    return ankoView({ RecyclerView(it, null) }, 0, init)
}

inline fun ViewManager.recyclerView() = recyclerView {}

inline fun ViewManager.recyclerView(init: RecyclerView.() -> Unit): RecyclerView {
    return ankoView({ RecyclerView(it, null) }, 0, init)
}