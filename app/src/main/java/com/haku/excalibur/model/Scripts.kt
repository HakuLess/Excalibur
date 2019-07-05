package com.haku.excalibur.model

import kotlinx.serialization.Serializable

/**
 * Usage:
 *
 * Created by HaKu on 2019-06-24.
 */
@Serializable
data class Scripts(
    var items: ArrayList<ScriptItem> = arrayListOf()
)

@Serializable
data class ScriptItem(
    var name: String = "",
    var content: String = "",
    var gap: String = ""
)