package com.kyleduo.app.klab.foundation.utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.withSign

/**
 * @author kyleduo on 3/26/21
 */

/**
 * pow(abs(x), y) with the sign of x
 */
fun signedPow(x: Double, y: Double) = abs(x).pow(y).withSign(x)