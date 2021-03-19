package com.kyleduo.app.playground.logcat

import android.os.Handler
import android.os.Looper
import okio.buffer
import okio.source

/**
 * @author zhangduo on 3/20/21
 */
class LogcatViewer(
    private val callback: Callback
): Thread() {

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun run() {
        val proc = Runtime.getRuntime().exec("logcat")

        val source = proc.inputStream.source().buffer()

        while (!isInterrupted) {
            val line = source.readUtf8Line()
            line?.let {
                mainHandler.post {
                    callback.onItem(line)
                }
            }
        }
    }

}

interface Callback {
    fun onItem(content: String)
}
