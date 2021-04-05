package com.kyleduo.app.klab.m.smoothrect.path

import android.graphics.Path

/**
 * @auther kyleduo on 3/30/21
 */
abstract class AbsSmoothCornerPath: Path() {

    abstract fun make(width: Float, height: Float, cornerRadius: Float)
}