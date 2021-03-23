package com.kyleduo.app.klab.foundation

import android.app.Application
import android.content.Context

/**
 * @author kyleduo on 3/2/21
 */
object KLabApp {
    lateinit var app: Context
        private set

    fun init(application: Application) {
        if (!::app.isInitialized) {
            app = application
        }
    }
}
