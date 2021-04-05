package com.kyleduo.app.klab.m.smoothrect

import android.os.Bundle
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

        widthBar.title = "Width"
        widthBar.progress = 0.5f
        widthBar.onProgressChangedListener = object : TuningBar.OnProgressChangedListener {
            override fun onProgressChanged(progress: Float) {
                smoothRect.widthRatio = progress
            }
        }

        heightBar.title = "Height"
        heightBar.progress = 0.5f
        heightBar.onProgressChangedListener = object : TuningBar.OnProgressChangedListener {
            override fun onProgressChanged(progress: Float) {
                smoothRect.heightRatio = progress
            }
        }

//        radiusXBar.title = "Radius-X"
//        radiusXBar.progress = 0.5f
//        radiusXBar.onProgressChangedListener = object : TuningBar.OnProgressChangedListener {
//            override fun onProgressChanged(progress: Float) {
//                smoothRect.cornerRatius = progress
//            }
//        }
//
//        radiusYBar.title = "Radius-Y"
//        radiusYBar.progress = 0.5f
//        radiusYBar.onProgressChangedListener = object : TuningBar.OnProgressChangedListener {
//            override fun onProgressChanged(progress: Float) {
//                smoothRect.radiusY = progress
//            }
//        }

        radiusBar.title = "Radius-XY"
        radiusBar.progress = 0.5f
        radiusBar.onProgressChangedListener = object : TuningBar.OnProgressChangedListener {
            override fun onProgressChanged(progress: Float) {
                smoothRect.cornerRadius = progress
            }
        }
    }
}
