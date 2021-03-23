package com.kyleduo.app.klab.m.typeface

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import com.kyleduo.app.klab.foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_typeface.*

/**
 * @author kyleduo on 3/2/21
 */
class CustomTypeFaceActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_typeface)

        testSpan()

        title = "Custom Typeface in Span"
    }

    private fun testSpan() {
        content.text = createSpan()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun createSpan(): CharSequence {
        return SpannableStringBuilder().apply {
            append("prefix")
            @Suppress("DEPRECATION")
            append(
                "icon",
                CustomTypeFaceSpan(
                    resources.getDrawable(R.drawable.typeface_test_icon),
                    "10"
                ),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            append("mid")
            @Suppress("DEPRECATION")
            append(
                "icon",
                CustomTypeFaceSpan(
                    resources.getDrawable(R.drawable.typeface_test_icon),
                    "5"
                ),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            append("10suffix")
        }
    }
}
