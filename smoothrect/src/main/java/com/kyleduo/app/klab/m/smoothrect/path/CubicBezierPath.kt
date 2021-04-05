package com.kyleduo.app.klab.m.smoothrect.path

import android.graphics.RectF
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * @auther zhangduo on 3/30/21
 */
class CubicBezierPath : AbsSmoothCornerPath() {
    companion object {
        /**
         * radius -> actual
         */
        private const val RADIUS_RATIO = 1.5959f

        private const val d0 = 0f
        private const val da = 0.3734f
        private const val db = 0.2111f
        private const val dab = da + db
        private const val dbb = db + db
        private const val dabb = dab + db
    }

    private val tempRectF = RectF()


//    private val cp0 = ControlPoint(
//            ComposeFactorBuilder.from(0f)
//                    .to(0.619534f).factor((-0.281943f).asFactor())
//                    .to(0.919020f).factor(linearFactor(-0.689202f, -0.657364f))
//                    .to(1f).factor(linearFactor(-1.057184f, -1.057771f)).build(),
//            1f.asFactor()
//    )
//
//
//    private val cp1 = ControlPoint(
//            ComposeFactorBuilder.from(0f)
//                    .to(0.619534f).factor(0.163817f.asFactor())
//                    .to(0.903024f).factor(linearFactor(0.245270f, -0.131475f))
//                    .to(1f).factor(linearFactor(-0.411910f, -0.596280f)).build(),
//            1f.asFactor()
//    )
//
//    private val cp2 = ControlPoint(
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904907f).factor(0.325460f.asFactor())
//                    .to(1f).factor(linearFactor(0.027671f, 0.329082f)).build(),
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904927f).factor(1.953587f.asFactor())
//                    .to(1f).factor(linearFactor(1.985500f, -0.035265f)).build()
//    )
//
//    private val cp3 = ControlPoint(
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904906f).factor(0.488423f.asFactor())
//                    .to(1f).factor(linearFactor(0.332442f, 0.172372f)).build(),
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904878f).factor(1.866434f.asFactor())
//                    .to(1f).factor(linearFactor(1.894788f, -0.031335f)).build()
//    )
//
//    private val cp4 = ControlPoint(
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904929f).factor(0.651386f.asFactor())
//                    .to(1f).factor(linearFactor(0.637202f, -0.015674f)).build(),
//            ComposeFactorBuilder.from(0f)
//                    .to(0.904913f).factor(1.779280f.asFactor())
//                    .to(1f).factor(linearFactor(1.804098f, -0.027425f)).build()
//    )

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
                    listOf(LinearMapping(1f, -0.674540f), LinearMapping(0.688886f, -0.332315f))),
            SegmentMapping(
                    listOf(0.909096f),
                    listOf(LinearMapping(1f, -0.046413f), LinearMapping(1.033334f, -0.083080f)))
    )

    private val cp3 = ControlPoint(
            SegmentMapping(
                    listOf(0.909093f),
                    listOf(LinearMapping(1f, -0.511577f), LinearMapping(0.837031f, -0.332312f))),
            SegmentMapping(
                    listOf(0.909100f),
                    listOf(LinearMapping(1f, -0.133566f), LinearMapping(1.029640f, -0.166170f)))
    )

    private val cp4 = ControlPoint(
            SegmentMapping(
                    listOf(0.909095f),
                    listOf(LinearMapping(1f, -0.348614f), LinearMapping(0.985187f, -0.332320f))),
            SegmentMapping(
                    listOf(0.909074f),
                    listOf(LinearMapping(1f, -0.220720f), LinearMapping(1.025918f, -0.249230f)))
    )
    private val cp5 = MirrorControlPoint(cp4)
    private val cp6 = MirrorControlPoint(cp3)
    private val cp7 = MirrorControlPoint(cp2)
    private val cp8 = MirrorControlPoint(cp1)
    private val cp9 = MirrorControlPoint(cp0)

    val cps = listOf(cp0, cp1, cp2, cp3, cp4)

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

//        val radiusRatioAdjustX = if (cornerRadiusX < cx / 2) {
//            RADIUS_RATIO
//        } else {
//            (cornerRadiusX / cx) * (2 - 2 * RADIUS_RATIO) + 2 * RADIUS_RATIO - 1
//        }
//
//        val radiusRatioAdjustY = if (cornerRadiusY < cy / 2) {
//            RADIUS_RATIO
//        } else {
//            (cornerRadiusY / cy) * (2 - 2 * RADIUS_RATIO) + 2 * RADIUS_RATIO - 1
//        }

//        tempRectF.set(0f, 0f, width, height)
//        addRect(tempRectF, Direction.CW)

//        val rx = if (cornerRadiusX < cx / 2) {
//            min(cornerRadiusX * RADIUS_RATIO, cx)
//        } else {
//            (2 - RADIUS_RATIO) * cornerRadiusX + (RADIUS_RATIO - 1) * cx
//        }
//
//        val ry = if (cornerRadiusY < cy / 2) {
//            min(cornerRadiusY * RADIUS_RATIO, cy)
//        } else {
//            (2 - RADIUS_RATIO) * cornerRadiusY + (RADIUS_RATIO - 1) * cy
//        }
        val cr = min(max(0f, cornerRadius), min(width / 2, height / 2))

        val radius = min(cx, cy)

        val xDiff = max(0f, cx - cy)
        val yDiff = max(0f, cy - cx)

        val radiusRatio = cr / radius

        for (cp in cps) {
            cp.radiusRatio = radiusRatio
            cp.radius = radius
        }

        // move to top center
//        moveTo(cx, 0f)
//        if (xDiff > 0) {
//            // move to first control point
//            rLineTo(xDiff, 0f)
//        }
//        rCubicToByRatio(rx, ry, cp0.x, cp0.y, cp1.x, cp1.y, cp2.x, cp2.y)
//        rCubicToByRatio(rx, ry, cp3.x, cp3.y, cp4.x, cp4.y, cp3.y, cp3.x)

//        cubicTo(width - cp0.x, cp0.y, width - cp1.x, cp1.y, width - cp2.x, cp2.y)
//        cubicTo(width - cp3.x, cp3.y, width - cp4.x, cp4.y, width - cp3.y, cp3.x)


//        val cps = listOf(cp0, cp1, cp2, cp3, cp4)
//        cps.forEach {
//            addCircle(cx + it.x * cx, cy - it.y * cy, 4f, Direction.CW)
//        }
//        cps.reversed().forEach {
//            addCircle(cx + it.y * cx, cy - it.x * cy, 4f, Direction.CW)
//        }

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

//        moveTo(width - cp0.x * radius, cp0.y * radius)
//        cubicTo(width - cp1.x * radius, cp1.y * radius, width - cp2.x * radius, cp2.y * radius, width - cp3.x * radius, cp3.y * radius)
//        cubicTo(width - cp4.x * radius, cp4.y * radius, width - cp4.y * radius, cp4.x * radius, width - cp3.y * radius, cp3.x * radius)
//        cubicTo(width - cp2.y * radius, cp2.x * radius, width - cp1.y * radius, cp1.x * radius, width - cp0.y * radius, cp0.x * radius)
//        close()

//        if (yDiff > 0) {
//            rLineTo(d0, 2 * yDiff)
//        }

//        // bottom right
//        rCubicToByRatio(rx, ry, d0, da, d0, dab, -db, dabb)
//        rCubicToByRatio(rx, ry, -db, db, -dbb, db, -dabb, db)
//
//        if (xDiff > 0) {
//            rLineTo(-2 * xDiff, d0)
//        }
//
//        // bottom left
//        rCubicToByRatio(rx, ry, -da, d0, -dab, d0, -dabb, -db)
//        rCubicToByRatio(rx, ry, -db, -db, -db, -dbb, -db, -dabb)
//
//        if (yDiff > 0) {
//            rLineTo(d0, -2 * yDiff)
//        }
//
//        // top left
//        rCubicToByRatio(rx, ry, d0, -da, d0, -dab, db, -dabb)
//        rCubicToByRatio(rx, ry, db, -db, dbb, -db, dabb, -db)
//        if (xDiff > 0) {
//            rLineTo(xDiff, 0f)
//        }

//        close()
    }

//    private fun rCubicToByRatio(
//            x: Float, y: Float,
//            rx1: Float, ry1: Float,
//            rx2: Float, ry2: Float,
//            rx3: Float, ry3: Float
//    ) {
//        rCubicTo(x * rx1, y * ry1, x * rx2, y * ry2, x * rx3, y * ry3)
//    }
//
//    private fun cubicToByRatio(
//            x: Float, y: Float,
//            rx1: Float, ry1: Float,
//            rx2: Float, ry2: Float,
//            rx3: Float, ry3: Float
//    ) {
//        rCubicTo(x * rx1, y * ry1, x * rx2, y * ry2, x * rx3, y * ry3)
//    }

    private fun cubicByControlPoint(
            cx: Float, cy: Float,
            xSign: Int, ySign: Int,
            x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float
    ) {
        cubicTo(
                cx + xSign * x1, cy + ySign * y1,
                cx + xSign * x2, cy + ySign * y2,
                cx + xSign * x3, cy + ySign * y3
        )
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

    private fun Float.approximateTo(another: Float) = abs(this - another) < 0.000001f
}