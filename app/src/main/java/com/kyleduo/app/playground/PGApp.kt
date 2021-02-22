package com.kyleduo.app.playground

import android.app.Application
import android.content.Context

/**
 * @author zhangduo on 2/22/21
 */
class PGApp : Application() {

    private lateinit var _app: Context
    val app: Context by lazy {
        _app
    }

    override fun onCreate() {
        super.onCreate()

        if (!this::_app.isInitialized) {
            _app = this
        }
    }

}
