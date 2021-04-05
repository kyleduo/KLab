package com.kyleduo.app.klab.m.smoothrect

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.kyleduo.app.klab.foundation.extensions.dp2px
import com.kyleduo.app.klab.m.smoothrect.path.CubicBezierPath
import com.kyleduo.app.klab.m.smoothrect.path.AbsSmoothCornerPath
import kotlin.math.min

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

    private val smoothPath: AbsSmoothCornerPath by lazy {
//        SuperellipsePath()
        CubicBezierPath()
    }

    private val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xfffed049.toInt()
            style = Paint.Style.FILL
            strokeWidth = 4f.dp2px()
            strokeJoin = Paint.Join.ROUND
        }
    }

    private val paint2 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFF0000FF.toInt()
            style = Paint.Style.FILL
            strokeWidth = 4f.dp2px()
            strokeJoin = Paint.Join.ROUND
        }
    }

    private val path = Path()

    var widthRatio: Float = 0.5f
        set(value) {
            field = value
            invalidate()
        }

    var heightRatio: Float = 0.5f
        set(value) {
            field = value
            invalidate()
        }

    var cornerRadius: Float = 0.5f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        canvas ?: return

//        preparePath()
//        canvas.drawPath(path, paint)

        val minSize = min(width, height)

        val w = minSize * widthRatio
        val h = minSize * heightRatio

        val rx = w * 0.5f * cornerRadius

        smoothPath.make(w, h, rx)

        canvas.save()
        canvas.translate((width - w) / 2, (height - h) / 2)
        canvas.drawRoundRect(RectF(0f, 0f, w, h), rx, rx, paint2)
        canvas.drawPath(smoothPath, paint)
//        canvas.drawRect(0f, 0f, w, h, paint2)
        canvas.restore()

    }
}
