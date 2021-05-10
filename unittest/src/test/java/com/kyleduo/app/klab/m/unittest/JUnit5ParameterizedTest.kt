package com.kyleduo.app.klab.m.unittest

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * @author kyleduo on 4/22/21
 */
class JUnit5ParameterizedTest {

    @ParameterizedTest
    @MethodSource("provideParams")
    fun `test equals`(first: Int, second: Int, expect: Boolean) {
        assert((first == second) == expect)
    }

    companion object {

        @JvmStatic
        fun provideParams(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(1, 2, false),
                Arguments.of(1, 1, true)
            )
        }
    }
}