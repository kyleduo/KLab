package com.kyleduo.app.klab.model

import android.app.Activity

/**
 * @author kyleduo on 3/2/21
 */
data class EntryItem(
        val name: String,
        val target: Class<out Activity>
)
