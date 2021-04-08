package com.kyleduo.app.klab.m.unittest

import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * @author kyleduo on 4/8/21
 */
class LifecycleExtensions : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext?) {
        println("BeforeAll from ${this.javaClass.simpleName}")
    }
}