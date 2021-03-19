package com.kyleduo.app.playground.logcat

import android.os.Bundle
import com.kyleduo.app.playground.common.BaseActivity
import java.util.*

/**
 * @author zhangduo on 3/20/21
 */
class LogcatActivity : BaseActivity(), Callback {

    private var viewer: LogcatViewer? = null
    private val lines = LinkedList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_logcat)

    }

    override fun onItem(content: String) {
        lines.add(0, content)


//        logcatConsole.text = StringBuilder().apply {
//            for (line in lines) {
//                append(line).append("\n")
//            }
//        }.toString()
    }

    override fun onStart() {
        super.onStart()
        viewer = LogcatViewer(this).also {
            it.start()
        }
    }

    override fun onStop() {
        super.onStop()
        viewer?.interrupt()
    }

}
