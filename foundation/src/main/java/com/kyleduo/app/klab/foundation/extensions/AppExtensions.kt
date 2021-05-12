package com.kyleduo.app.klab.foundation.extensions

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author kyleduo on 5/12/21
 */

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)

fun Context.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    context?.showToast(message, duration)
}