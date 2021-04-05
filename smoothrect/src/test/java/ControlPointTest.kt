package com.kyleduo.app.klab.m.smoothrect.path

import org.junit.Test
import kotlin.math.abs

/**
 * @auther zhangduo on 4/5/21
 */
internal class ControlPointTest {

    @Test
    fun testControlPointFactor() {
        val cp0x = ComposeFactorBuilder.from(0f)
                .to(0.619534f).factor(linearFactor(1f, -1.281943f))
                .to(0.919020f).factor(linearFactor(-0.689202f, 0.657364f))
                .to(1f).factor(linearFactor(-1.057184f, 1.057771f)).build()

        val y065 = cp0x.eval(0.619534f)
        println(y065)
    }
}