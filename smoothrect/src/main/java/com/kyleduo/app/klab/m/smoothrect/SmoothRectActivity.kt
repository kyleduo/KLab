package com.kyleduo.app.klab.m.smoothrect

import android.os.Bundle
import android.widget.SeekBar
import com.kyleduo.app.klab.foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_smooth_rect.*

/**
 * @author zhangduo on 3/25/21
 */
class SmoothRectActivity : BaseActivity() {

    companion object {
        private const val TAG = "SmoothRectActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_smooth_rect)

        smoothRect.n = progressToN(nSeekBar.progress)
        nTv.text = smoothRect.n.toString()
        nSeekBar.setOnSeekBarChangeListener(ProgressChangedAdapter {
            smoothRect.n = progressToN(it)
            nTv.text = smoothRect.n.toString()
        })

        smoothRect.rx = progressToRadius(rxSeekBar.progress, rxSeekBar.max)
        rxTv.text = smoothRect.rx.toString()
        rxSeekBar.setOnSeekBarChangeListener(ProgressChangedAdapter {
            smoothRect.rx = progressToRadius(it, rxSeekBar.max)
            rxTv.text = smoothRect.rx.toString()
        })

        smoothRect.ry = progressToRadius(rySeekBar.progress, rySeekBar.max)
        ryTv.text = smoothRect.ry.toString()
        rySeekBar.setOnSeekBarChangeListener(ProgressChangedAdapter {
            smoothRect.ry = progressToRadius(it, rySeekBar.max)
            ryTv.text = smoothRect.ry.toString()
        })

        smoothRect.radius = progressToCornerRadius(cornerSeekBar.progress, cornerSeekBar.max)
        cornerTv.text = smoothRect.radius.toString()
        cornerSeekBar.setOnSeekBarChangeListener(ProgressChangedAdapter {
            smoothRect.radius = progressToCornerRadius(it, cornerSeekBar.max)
            cornerTv.text = smoothRect.radius.toString()
        })

    }

    private fun progressToN(progress: Int): Float {
        return (0 + 10 * progress.toDouble() / nSeekBar.max).toFloat()
    }

    private fun progressToRadius(progress: Int, max: Int): Float {
        return (progress * 1.0f / max) * 2f
    }

    private fun progressToCornerRadius(progress: Int, max: Int): Float {
        return progress * 1.0f / max
    }

    class ProgressChangedAdapter(
        private val handler: ((progress: Int) -> Unit)
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            handler(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }
}
