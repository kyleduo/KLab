package com.kyleduo.app.klab

import android.app.Application
import com.didichuxing.doraemonkit.DoraemonKit
import com.kyleduo.app.klab.foundation.KLabApp

/**
 * @author kyleduo on 2/22/21
 */
class KLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KLabApp.init(this)

        DoraemonKit.install(this)
    }

}
