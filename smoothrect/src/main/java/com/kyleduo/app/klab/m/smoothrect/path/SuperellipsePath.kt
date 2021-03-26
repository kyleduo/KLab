package com.kyleduo.app.klab.m.smoothrect.path

import android.graphics.Path
import com.kyleduo.app.klab.foundation.utils.signedPow
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * @author kyleduo on 3/26/21
 */
class SuperellipsePath(
    var power: Float = 4f
) : Path() {

    /**
     * make a superellipse
     */
    fun make(width: Float, height: Float, cornerRadiusX: Float, cornerRadiusY: Float) {
        reset()

        val cx = width / 2
        val cy = height / 2

        // radius of superellipse
        val rx = cx
        val ry = cy

        // corner radius
        val crx = min(cornerRadiusX * 1.5f, rx)
        val cry = min(cornerRadiusY * 1.5f, ry)

//        val rx = min(cx, cy) / 2 * rx
//        val ry = min(cx, cy) / 2 * ry
//        val p = if (n > 90f) 0.0 else 2.0 / n
        val p = 2 / power.toDouble()

        // adjust p for cases that rx != ry
//        val px = if (rx > ry) {
//            p * ry / rx
//        } else p
//        val py = if (ry > rx) {
//            p * rx / ry
//        } else p
        val px = p; val py = p

        if (rx == 0f || ry == 0f) {
            return
        }
//        Log.d(TAG, "preparePath: p: $p, w: $w, h: $h, cx: $cx, cy: $cy, rx: $rx, ry: $ry")

        for (a in 0..90) {
            val cxAdjust = width - crx
            val cyAdjust = height - cry
            val x = (cxAdjust + crx * signedPow(cos(a / 360.0 * Math.PI * 2), px)).toFloat()
            val y = (cyAdjust + cry * signedPow(sin(a / 360.0 * Math.PI * 2), py)).toFloat()
            if (a == 0) {
                moveTo(x, y)
            } else {
                lineTo(x, y)
            }
        }
        close()
    }
}
