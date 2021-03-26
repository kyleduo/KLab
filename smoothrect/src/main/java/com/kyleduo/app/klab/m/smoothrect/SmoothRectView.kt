package com.kyleduo.app.klab.m.smoothrect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.kyleduo.app.klab.foundation.extensions.dp2px
import com.kyleduo.app.klab.foundation.utils.signedPow
import com.kyleduo.app.klab.m.smoothrect.path.SuperellipsePath
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * @author kyleduo on 3/25/21
 */
class SmoothRectView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "SmoothRectView"
    }

    private val smoothPath = SuperellipsePath(2.5f)

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xfffed049.toInt()
            style = Paint.Style.FILL
            strokeWidth = 4.dp2px()
            strokeJoin = Paint.Join.ROUND
        }
    }

    private val paint2 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x7f3ed049.toInt()
            style = Paint.Style.FILL
            strokeWidth = 4.dp2px()
            strokeJoin = Paint.Join.ROUND
        }
    }

    private val path = Path()
    var power: Float = 2f
        set(value) {
            field = value
            smoothPath.power = field
            invalidate()
        }

    var rx: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    var ry: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    var radius: Float = 0.5f
        set(value) {
            field = min(1f, max(0f, value))
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

//        preparePath()
//        canvas.drawPath(path, paint)

        val minSize = min(width, height)

        val w = minSize * 0.5f * rx
        val h = minSize * 0.5f * ry

        val rx = w * 0.25f
        val ry = h * 0.25f

        smoothPath.make(w, h, rx, ry)

        canvas.save()
        canvas.translate((width - w) / 2, (height - h) / 2)
        canvas.drawPath(smoothPath, paint)
        canvas.drawRoundRect(RectF(0f, 0f, w, h), rx, ry, paint2)
        canvas.restore()

    }

    private fun preparePath() {
        path.reset()

        val w = width.toFloat() - paddingLeft - paddingRight
        val h = height.toFloat() - paddingTop - paddingBottom
        val cx = w / 2 + paddingStart
        val cy = h / 2 + paddingTop

        val rx = min(cx, cy) / 2 * rx
        val ry = min(cx, cy) / 2 * ry
        val p = if (power > 90f) 0.0 else 2.0 / power

        // adjust p for cases that rx != ry
        val px = if (rx > ry) {
            p * ry / rx
        } else p
        val py = if (ry > rx) {
            p * rx / ry
        } else p

        if (rx == 0f || ry == 0f) {
            return
        }
//        Log.d(TAG, "preparePath: p: $p, w: $w, h: $h, cx: $cx, cy: $cy, rx: $rx, ry: $ry")

        // the points is so closed to each other so we can not see the straight line between them
        // when reduce the count to 30, we can see the lines.
        for (a in 0..360) {
            val x = (cx + rx * signedPow(cos(a / 360.0 * Math.PI * 2), px)).toFloat()
            val y = (cy + ry * signedPow(sin(a / 360.0 * Math.PI * 2), py)).toFloat()
            if (a == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }
        path.close()
    }
}
