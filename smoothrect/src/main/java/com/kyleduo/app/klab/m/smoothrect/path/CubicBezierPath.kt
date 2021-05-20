package com.kyleduo.app.klab.m.smoothrect.path

import android.graphics.RectF
import kotlin.math.max
import kotlin.math.min

/**
 * @auther kyleduo on 3/30/21
 */
class CubicBezierPath : AbsSmoothCornerPath() {
    private val tempRectF = RectF()

    private val cp0 = ControlPoint(
        SegmentMapping(
            listOf(0.636643f, 0.909091f),
            listOf(
                LinearMapping(1f, -1.281942f),
                LinearMapping(0.613495f, -0.674845f),
                0f.toMapping()
            )
        ),
        1f.toMapping()
    )

    private val cp1 = ControlPoint(
        SegmentMapping(
            listOf(0.636645f, 0.909090f),
            listOf(
                LinearMapping(1f, -0.836183f),
                LinearMapping(1.077301f, -0.957603f),
                LinearMapping(0.433340f, -0.249245f)
            )
        ),
        1f.toMapping()
    )

    private val cp2 = ControlPoint(
        SegmentMapping(
            listOf(0.909092f),
            listOf(LinearMapping(1f, -0.674540f), LinearMapping(0.688886f, -0.332315f))
        ),
        SegmentMapping(
            listOf(0.909096f),
            listOf(LinearMapping(1f, -0.046413f), LinearMapping(1.033334f, -0.083080f))
        )
    )

    private val cp3 = ControlPoint(
        SegmentMapping(
            listOf(0.909093f),
            listOf(LinearMapping(1f, -0.511577f), LinearMapping(0.837031f, -0.332312f))
        ),
        SegmentMapping(
            listOf(0.909100f),
            listOf(LinearMapping(1f, -0.133566f), LinearMapping(1.029640f, -0.166170f))
        )
    )

    private val cp4 = ControlPoint(
        SegmentMapping(
            listOf(0.909095f),
            listOf(LinearMapping(1f, -0.348614f), LinearMapping(0.985187f, -0.332320f))
        ),
        SegmentMapping(
            listOf(0.909074f),
            listOf(LinearMapping(1f, -0.220720f), LinearMapping(1.025918f, -0.249230f))
        )
    )
    private val cp5 = MirrorControlPoint(cp4)
    private val cp6 = MirrorControlPoint(cp3)
    private val cp7 = MirrorControlPoint(cp2)
    private val cp8 = MirrorControlPoint(cp1)
    private val cp9 = MirrorControlPoint(cp0)

    private val cps = listOf(cp0, cp1, cp2, cp3, cp4)

    override fun make(width: Float, height: Float, cornerRadius: Float) {
        reset()

        if (width == 0f || height == 0f) {
            return
        }

        val cx = width / 2
        val cy = height / 2

        // corner radius all larger than shape radius, just draw oval
        if (cornerRadius >= cx || cornerRadius >= cy) {
            tempRectF.set(0f, 0f, width, height)
            val radius = min(cx, cy)
            addRoundRect(tempRectF, radius, radius, Direction.CW)
            return
        }

        if (cornerRadius == 0f) {
            tempRectF.set(0f, 0f, width, height)
            addRect(tempRectF, Direction.CW)
            return
        }
        val cr = min(max(0f, cornerRadius), min(width / 2, height / 2))

        val radius = min(cx, cy)

        val xDiff = max(0f, cx - cy)
        val yDiff = max(0f, cy - cx)

        val radiusRatio = cr / radius

        for (cp in cps) {
            cp.radiusRatio = radiusRatio
            cp.radius = radius
        }

        moveTo(cx + xDiff + cp0.x, cy - yDiff - cp0.y)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, -1, cp1, cp2, cp3)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, -1, cp4, cp5, cp6)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, -1, cp7, cp8, cp9)

        lineTo(cx + xDiff + cp9.x, cy + yDiff + cp9.y)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, 1, cp8, cp7, cp6)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, 1, cp5, cp4, cp3)
        cubicByControlPoint(cx, cy, xDiff, yDiff, 1, 1, cp2, cp1, cp0)

        lineTo(cx - xDiff - cp0.x, cy + yDiff + cp0.y)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, 1, cp1, cp2, cp3)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, 1, cp4, cp5, cp6)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, 1, cp7, cp8, cp9)

        lineTo(cx - xDiff - cp9.x, cy - yDiff - cp9.y)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, -1, cp8, cp7, cp6)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, -1, cp5, cp4, cp3)
        cubicByControlPoint(cx, cy, xDiff, yDiff, -1, -1, cp2, cp1, cp0)

        close()
    }

    private fun cubicByControlPoint(
        cx: Float, cy: Float,
        xDiff: Float, yDiff: Float,
        xSign: Int, ySign: Int,
        cp1: IControlPoint, cp2: IControlPoint, cp3: IControlPoint
    ) {
        cubicTo(
            cx + xSign * xDiff + xSign * cp1.x, cy + ySign * yDiff + ySign * cp1.y,
            cx + xSign * xDiff + xSign * cp2.x, cy + ySign * yDiff + ySign * cp2.y,
            cx + xSign * xDiff + xSign * cp3.x, cy + ySign * yDiff + ySign * cp3.y
        )
    }
}