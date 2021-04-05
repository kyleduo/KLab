package com.kyleduo.app.klab.m.smoothrect.path

/**
 * @auther kyleduo on 4/2/21
 */

//abstract class ControlPointEvaluator(
//        private val factors: List<Triple<Float, Float, Float>>
//) {
//    var minRadius: Float = 0f
//        set(value) {
//            field = value
//            updateRadiusRatio()
//        }
//    var cornerRadius: Float = 0f
//        set(value) {
//            field = value
//            updateRadiusRatio()
//        }
//    private var radiusRatio: Float = 0f
//
//    private fun updateRadiusRatio() {
//        radiusRatio = if (minRadius == 0f) {
//            0f
//        } else {
//            min(1f, max(0f, cornerRadius / minRadius))
//        }
//    }
//
//    val x: Float
//        get() = factors.first { radiusRatio >= it.first }.evaluate()
//
//    private fun evaluate(a: Float, b: Float): Float {
//        return a + b * radiusRatio
//    }
//
//    private fun Triple<Float, Float, Float>.evaluate(): Float {
//        return if (first == 0f) {
//            second
//        } else {
//            evaluate(second, third)
//        }
//    }
//}
//
//class P0 : ControlPointEvaluator(
//        listOf(
//                Triple(0.919020f, 2.057184f, -1.057771f),
//                Triple(0.619534f, 1.689202f, -0.657364f),
//                Triple(0f, 1.281943f, 0f)
//        )
//)
//
//class P1 : ControlPointEvaluator(
//        listOf(
//                Triple(0.903024f, 1.411910f, -0.596280f),
//                Triple(0.619534f, 0.754730f, 0.131475f),
//                Triple(0f, 0.836183f, 0f)
//        )
//)

interface IFactor {
    fun eval(input: Float): Float
}

class LinearFactor(
        private val a: Float,
        private val b: Float
) : IFactor {
    override fun eval(input: Float): Float {
        return a + b * input
    }
}

class ConstantFactor(
        private val constant: Float
) : IFactor {
    override fun eval(input: Float): Float {
        return constant
    }
}

/**
 * [min, max)
 */
class ConditionFactor(
        private val min: Float,
        private val max: Float,
        private val factor: IFactor
) : IFactor {

    fun isSupport(input: Float) = input >= min && input < max

    override fun eval(input: Float) = factor.eval(input)

}

class ComposeFactor(
        private val factors: List<ConditionFactor>
) : IFactor {

    override fun eval(input: Float): Float {
        val factor = factors.firstOrNull { it.isSupport(input) }
        return factor?.eval(input) ?: factors.last().eval(input)
    }

}

//class ControlPoint(
//        private val xFactor: IFactor,
//        private val yFactor: IFactor
//) {
//
//    var radiusRatio: Float = 0f
//
//    val x: Float
//        get() = xFactor.eval(radiusRatio)
//
//    val y: Float
//        get() = yFactor.eval(radiusRatio)
//}

fun Float.asFactor() = ConstantFactor(this)

fun IFactor.withCondition(min: Float, max: Float) = ConditionFactor(min, max, this)

fun Float.asLinearFactor(b: Float) = LinearFactor(this, b)

fun linearFactor(a: Float, b: Float) = LinearFactor(a, b)

class ComposeFactorBuilder(start: Float) {
    private var fromPoint = start
    private val factorList = mutableListOf<ConditionFactor>()
    private var toPoint: Float = start
    private var segmentList = mutableListOf<Pair<Float, IFactor>>()

    fun build(): ComposeFactor {
//        segmentList.sortByDescending { it.first }
//        var lastMax = 1f
//        for (pair in segmentList) {
//            factorList.add(ConditionFactor(pair.first, lastMax, pair.second))
//            lastMax = pair.first
//        }
        return ComposeFactor(factorList)
    }

    fun to(breakPoint: Float): ComposeFactorBuilder {
        this.toPoint = breakPoint
        return this
    }

    fun factor(factor: IFactor): ComposeFactorBuilder {
        factorList.add(ConditionFactor(fromPoint, toPoint, factor))
        fromPoint = toPoint
        return this
    }

//    fun addBreakPoint(breakPoint: Float, factor: IFactor): ComposeFactorBuilder {
//        segmentList.add(Pair(breakPoint, factor))
//        return this
//    }
//
//    fun addDefault(factor: IFactor): ComposeFactorBuilder {
//        return addBreakPoint(0f, factor)
//    }

    companion object {
        fun from(breakPoint: Float): ComposeFactorBuilder {
            return ComposeFactorBuilder(breakPoint)
        }
    }
}

fun Float.builder() = ComposeFactorBuilder(this)


interface Mapping {
    fun map(value: Float): Float
}

class ConstantMapping(
        private val constant: Float
) : Mapping {
    override fun map(value: Float): Float = constant
}

class LinearMapping(
        private val a: Float,
        private val b: Float
) : Mapping {
    override fun map(value: Float): Float = a + b * value
}

class SegmentMapping(
        breakPoints: List<Float>,
        mappings: List<Mapping>
) : Mapping {

    private val segments = mutableListOf<Pair<Float, Mapping>>()

    init {
        if (breakPoints.size != mappings.size - 1) {
            error("Params error, breakPoints should just less 1 than mappings")
        }
        breakPoints.withIndex().forEach {
            segments.add(Pair(it.value, mappings[it.index]))
        }
        segments.add(Pair(1f, mappings.last()))
    }

    override fun map(value: Float): Float {
        return segments.first { value < it.first }.second.map(value)
    }
}

fun Float.toMapping(): ConstantMapping = ConstantMapping(this)

interface IControlPoint {

    /**
     * Rc / R
     */
    var radiusRatio: Float

    /**
     * R
     */
    var radius: Float

    val x: Float

    val y: Float
}

class ControlPoint(
        private val xMapping: Mapping,
        private val yMapping: Mapping
) : IControlPoint {

    override var radiusRatio: Float = 0f

    override var radius: Float = 0f

    override val x: Float
        get() = xMapping.map(radiusRatio) * radius
    override val y: Float
        get() = yMapping.map(radiusRatio) * radius
}

class MirrorControlPoint(
        private val controlPoint: ControlPoint
) : IControlPoint {
    override var radiusRatio: Float
        get() = controlPoint.radiusRatio
        set(value) {
            controlPoint.radiusRatio = value
        }
    override var radius: Float
        get() = controlPoint.radius
        set(value) {
            controlPoint.radius = value
        }
    override val x: Float
        get() = controlPoint.y
    override val y: Float
        get() = controlPoint.x

}