package com.haku.excalibur.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.haku.excalibur.model.Scripts
import kotlinx.serialization.json.Json


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-25.
 */
object SpUtils {

    private const val NAME = "Scripts"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val mScriptData = Pair("Scripts", "{}")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit() and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

    var scripts: Scripts
        get() = Json.parse(Scripts.serializer(), preferences.getString(mScriptData.first, mScriptData.second)!!)
        set(value) = preferences.edit {
            Log.e("HaKu", "what: ${Json.stringify(Scripts.serializer(), value)}")
            it.putString(mScriptData.first, Json.stringify(Scripts.serializer(), value))
        }
}