package com.kyleduo.app.klab.m.smoothrect

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView

/**
 * @auther kyleduo on 3/30/21
 */
class TuningBar @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), SeekBar.OnSeekBarChangeListener {

    private lateinit var titleTv: TextView
    private lateinit var seekBar: SeekBar

    var title: String = ""
        set(value) {
            field = value
            updateTitle()
        }
    var onProgressChangedListener: OnProgressChangedListener? = null
    var progress: Float = 0f
        set(value) {
            field = value
            seekBar.progress = (value * seekBar.max).toInt()
        }

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_tuning_bar, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        titleTv = findViewById(R.id.tuningBarTitle)
        seekBar = findViewById(R.id.tuningBarProgress)
        seekBar.setOnSeekBarChangeListener(this)
        this.progress = seekBar.progress * 1f / seekBar.max
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        this.progress = progress * 1f / this.seekBar.max
        updateTitle()
        onProgressChangedListener?.onProgressChanged(this.progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    @SuppressLint("SetTextI18n")
    private fun updateTitle() {
        titleTv.text = "$title: $progress"
    }

    interface OnProgressChangedListener {
        /**
         * [progress] [0..1]
         */
        fun onProgressChanged(progress: Float)
    }
}