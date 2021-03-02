package com.kyleduo.app.playground.common.extensions

import com.kyleduo.app.playground.common.PGApp


fun Int.dp2px() = PGApp.app.resources.displayMetrics.density * this

fun Float.dp2px() = PGApp.app.resources.displayMetrics.density * this
