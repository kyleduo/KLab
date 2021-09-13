package com.kyleduo.app.klab.m.generalui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author kyleduo on 2021/9/13
 */
class PathArc @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val radius = 16f * context.resources.displayMetrics.density
    private val tempRect = RectF()

    private val path = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.CYAN
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        preparePath()
    }

    private fun preparePath() {
        path.reset()
        path.moveTo(0f, radius)
        path.lineTo(0f, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), radius)

        tempRect.set(width - radius * 2, 0f, width.toFloat(), radius * 2)
        // line to right is 0 degree, sweep is the same as clock direction
        path.arcTo(tempRect, 0f, -90f)

        path.lineTo(radius, 0f)

        tempRect.set(0f, 0f, radius * 2, radius * 2)
        path.arcTo(tempRect, -90f, -90f)

        path.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }


}