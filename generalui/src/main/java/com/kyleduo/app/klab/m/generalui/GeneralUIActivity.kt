package com.kyleduo.app.klab.m.generalui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import com.kyleduo.app.klab.foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_general_ui.*
import kotlin.random.Random

/**
 * @author kyleduo on 4/12/21
 */
class GeneralUIActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_general_ui)

        animateText.setOnClickListener {
            startAnimation()
            val startTime = SystemClock.elapsedRealtime()
            animateView.postDelayed({
                val location = IntArray(2)
                animateText.getLocationOnScreen(location)
                animateText.text =
                    "After ${SystemClock.elapsedRealtime() - startTime} milliseconds.\n" +
                            "Location: (${location[0]}, ${location[1]})\n" +
                            "Size: (${animateText.width}, ${animateText.height})"
            }, 400L + Random.nextLong(400))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun startAnimation() {
        animateText.text = ""
        animateView.pivotX = 0f
        animateView.scaleX = 0f
        animateView.animate().scaleX(1f).setDuration(1000L).withEndAction {
            val location = IntArray(2)
            animateText.getLocationOnScreen(location)
            animateText.text =
                "${animateText.text}\nActual location: (${location[0]}, ${location[1]})"
        }.start()
    }
}