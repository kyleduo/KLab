package com.kyleduo.app.playground.m.okio

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.kyleduo.app.playground.common.BaseActivity
import kotlinx.android.synthetic.main.activity_okio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.buffer
import okio.sink
import java.io.File
import java.lang.StringBuilder

/**
 * @auther zhangduo on 3/10/21
 */
class OkioActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_okio)

        lifecycleScope.launch(Dispatchers.IO) {
            val file = logTime("init") {
                createTestFile("test1")
            }

            val ok = OkioOperator()
            val jdk = JdkOperator()

            logTime("read - jdk") {
                jdk.readFile(file)
            }
            logTime("read - okio") {
                ok.readFile(file)
            }

            logTime("copy - okio") {
                ok.copyFile(file, createDstFile("io"))
            }
            logTime("copy - jdk") {
                jdk.copyFile(file, createDstFile("jdk"))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun output(content: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            outputTv.text = outputTv.text.toString() + "\n" + content
        }
    }

    private fun createDstFile(name: String): File {
        val file = File(externalCacheDir, name)
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        return file
    }

    private fun createTestFile(name: String): File {
        val file = File(externalCacheDir, name)
        if (file.exists()) {
            return file
        }
        val buffer = file.sink().buffer()
        for (i in 0..1000000) {
            val sb = StringBuilder()
            for (j in 0..100) {
                sb.append(j % 10)
            }
            sb.append('\n')
            buffer.writeUtf8(sb.toString())
        }
        buffer.flush()
        buffer.close()
        return file
    }

    private fun <T> logTime(name: String, task: () -> T): T {
        val start = SystemClock.uptimeMillis()
        val ret = task()
        val end = SystemClock.uptimeMillis()

        val content = "$name, time: ${end - start}"
        Log.d("LogTime", content)
        output(content)
        return ret
    }
}