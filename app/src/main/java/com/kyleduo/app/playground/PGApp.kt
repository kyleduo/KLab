package com.kyleduo.app.playground

import android.app.Application
import com.kyleduo.app.playground.common.PGApp

/**
 * @author kyleduo on 2/22/21
 */
class PGApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PGApp.app = this
    }

}
