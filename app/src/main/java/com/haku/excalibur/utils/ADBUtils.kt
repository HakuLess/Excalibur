package com.haku.excalibur.utils

import android.util.Log
import com.haku.excalibur.model.ScriptItem
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.DataOutputStream
import java.io.OutputStream


/**
 * Usage:
 *
 * Created by HaKu on 2019-06-10.
 */
object ADBUtils {

    var curCmd = ScriptItem()

    private var process: Process? = null
    private var outputStream: OutputStream? = null
    private var dataOutputStream: DataOutputStream? = null

    private fun start() {
        process = Runtime.getRuntime().exec("su")
        outputStream = process?.outputStream
        dataOutputStream = DataOutputStream(outputStream)
    }

    private fun end() {
        process = null
        outputStream?.close()
        dataOutputStream?.close()
    }

    fun exec(cmd: String) {
        try {
            start()

            Log.e("HaKu", "cmd: $cmd")
            dataOutputStream?.writeBytes(cmd)
            dataOutputStream?.flush()

            end()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun stopShell() {
        exec("sh sdcard/scripts/stop_all.sh")
    }

    fun execRes(cmd: String): String {
        try {
//            Runtime.getRuntime().exec("su")
            val process = Runtime.getRuntime().exec(cmd)

            val reader = BufferedReader(InputStreamReader(process.inputStream)) // Output is empty
            var read: Int
            val buffer = CharArray(4096)
            val output = StringBuffer()

            read = reader.read(buffer)
            while (read > 0) {
                output.append(buffer, 0, read)
                read = reader.read(buffer)
            }
            reader.close()
            Log.e("HaKu", "output: $output")
            return output.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ""
        }
    }
}