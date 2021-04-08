package com.kyleduo.app.klab.m.unittest

import org.junit.jupiter.api.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Testing Junit5 test lifecycle
 */
class JUnit5Lifecycle {

    companion object {

        init {
            println("companion object init")
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            println("$count BeforeAll")
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            println("$count AfterAll")
        }

        private val counter = AtomicInteger(1)

        val count: String
            get() = "[${String.format("%2d", counter.getAndIncrement())}]"
    }

    init {
        println("$count Root Class, init")
    }

    @BeforeEach
    fun beforeEach() {
        println("$count Root Class, Before Each 2")
    }

    @Test
    fun testMethod1() {
        println("$count Root Class, Test Method 1 2")
    }

    @Test
    fun testMethod2() {
        println("$count Root Class, Test Method 2 2")
    }

    @AfterEach
    fun afterEach() {
        println("$count Root Class, After Each 2")
    }

    @Nested
    inner class NestedClass1 {

        init {
            println("$count ${this.hashCode()} Nested Class 1, init 2")
        }

        @BeforeEach
        fun beforeEach() {
            println("$count ${this.hashCode()} Nested Class 1, BeforeEach 2")
        }

        @Test
        fun testMethod1() {
            println("$count ${this.hashCode()} Nested Class 1, Test Method 1 2")
        }

        @Test
        fun testMethod2() {
            println("$count ${this.hashCode()} Nested Class 1, Test Method 2 2")
        }

        @AfterEach
        fun afterEach() {
            println("$count ${this.hashCode()} Nested Class 1, After Each 2")
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class NestedClass2 {

        init {
            println("$count ${this.hashCode()} Nested Class 2, init 2")
        }

        @BeforeAll
        fun beforeAll() {
            println("$count ${this.hashCode()} Nested Class 2, BeforeAll 2")
        }

        @BeforeEach
        fun beforeEach() {
            println("$count ${this.hashCode()} Nested Class 2, BeforeEach 2")
        }

        @Test
        fun testMethod1() {
            println("$count ${this.hashCode()} Nested Class 2, Test Method 1 2")
            Thread.sleep(500)
        }

        @Test
        fun testMethod2() {
            println("$count ${this.hashCode()} Nested Class 2, Test Method 2 2")
            Thread.sleep(500)
        }

        @AfterEach
        fun afterEach() {
            println("$count ${this.hashCode()} Nested Class 2, After Each 2")
        }

        @AfterAll
        fun afterAll() {
            println("$count ${this.hashCode()} Nested Class 2, AfterAll 2")
        }
    }
}