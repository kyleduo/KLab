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

    private val smoothPath = SuperellipsePath()

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

    var rx: Float = 0.5f
        set(value) {
            field = value
            invalidate()
        }

    var ry: Float = 0.5f
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

        val w = minSize * 0.5f
        val h = minSize * 0.5f

        val rx = w * 0.5f * rx
        val ry = h * 0.5f * ry

        smoothPath.make(w, h, rx, ry)

        canvas.save()
        canvas.translate((width - w) / 2, (height - h) / 2)
        canvas.drawPath(smoothPath, paint)
//        canvas.drawRoundRect(RectF(0f, 0f, w, h), rx, ry, paint2)
        canvas.restore()

    }
}
