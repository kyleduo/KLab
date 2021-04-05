package com.kyleduo.app.klab.m.smoothrect.path

import com.kyleduo.app.klab.foundation.utils.signedPow
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * @author kyleduo on 3/26/21
 */
class SuperellipsePath : AbsSmoothCornerPath() {

    /**
     * make a superellipse
     */
    override fun make(width: Float, height: Float, cornerRadius: Float) {
        reset()

        val cx = width / 2
        val cy = height / 2

        // radius of superellipse
        val rx = cx
        val ry = cy

        val crx = min(cornerRadius, rx) / rx
        val cry = min(cornerRadius, ry) / ry

        val px = crx.toDouble()
        val py = cry.toDouble()

        if (rx == 0f || ry == 0f) {
            return
        }

        for (a in 0..360) {
            val x = (cx + rx * signedPow(cos(a / 360.0 * Math.PI * 2), px)).toFloat()
            val y = (cy + ry * signedPow(sin(a / 360.0 * Math.PI * 2), py)).toFloat()
            if (a == 0) {
                moveTo(x, y)
            } else {
                lineTo(x, y)
            }
        }
        close()
    }
}
