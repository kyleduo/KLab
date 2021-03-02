package com.kyleduo.app.playground.m.typeface

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import com.kyleduo.app.playground.common.PGApp
import com.kyleduo.app.playground.common.extensions.dp2px

/**
 * @author kyleduo on 3/2/21
 */
class CustomTypeFaceSpan(
    private val drawable: Drawable,
    private val iconText: String
) : ReplacementSpan() {

    private val padding = 4.dp2px()

    private val typeFace by lazy {
        Typeface.createFromAsset(PGApp.app.assets, "fonts/custom_typeface_font.ttf")
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        if (drawable.bounds.width() == 0 && fm != null) {
            val lineHeight = fm.bottom - fm.top
            val width =
                (lineHeight * 1f / drawable.intrinsicHeight * drawable.intrinsicWidth).toInt()
            drawable.setBounds(0, 0, width, lineHeight)
        }
        return (drawable.bounds.width() + padding).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val count = canvas.save()
        val yOffset = (bottom - drawable.bounds.height() + 1) / 2
        canvas.translate(x + padding / 2, yOffset.toFloat())

        drawable.draw(canvas)

        val testNum = iconText
        val textWidth = paint.measureText(testNum)

        val textX =
            drawable.bounds.width() * 0.35f + (drawable.bounds.width() * 0.55f - textWidth) * 0.3f
        val textY = (y - yOffset).toFloat()

        paint.typeface = typeFace
        paint.color = Color.WHITE
        canvas.drawText(testNum, textX, textY, paint)

        canvas.restoreToCount(count)
    }
}
