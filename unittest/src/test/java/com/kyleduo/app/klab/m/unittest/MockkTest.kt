package com.kyleduo.app.klab.m.unittest

import io.mockk.every
import io.mockk.spyk
import org.junit.jupiter.api.Test

/**
 * @author kyleduo on 2021/6/16
 */
class MockkTest {

    val instance = WithLazy()
    val spyInstance = spyk(instance).also {
        every { it.name }.returns("spy")
    }

    @Test
    fun testLazy() {
        assert(spyInstance.testName() == "spy")
    }

    class WithLazy {
        val name by lazy {
            "name"
        }

        fun testName(): String {
            return name
        }
    }
}