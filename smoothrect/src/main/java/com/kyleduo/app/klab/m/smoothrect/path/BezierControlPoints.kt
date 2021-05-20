package com.kyleduo.app.klab.m.smoothrect.path

/**
 * @auther kyleduo on 4/2/21
 */

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

fun linearFactor(a: Float, b: Float) = LinearFactor(a, b)

class ComposeFactorBuilder(start: Float) {
    private var fromPoint = start
    private val factorList = mutableListOf<ConditionFactor>()
    private var toPoint: Float = start

    fun build(): ComposeFactor {
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

    companion object {
        fun from(breakPoint: Float): ComposeFactorBuilder {
            return ComposeFactorBuilder(breakPoint)
        }
    }
}


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