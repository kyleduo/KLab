package com.kyleduo.app.klab.foundation.extensions

import com.kyleduo.app.klab.foundation.KLabApp


fun Int.dp2px() = KLabApp.app.resources.displayMetrics.density * this

fun Float.dp2px() = KLabApp.app.resources.displayMetrics.density * this
