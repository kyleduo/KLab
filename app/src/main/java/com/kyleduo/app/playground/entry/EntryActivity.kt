package com.kyleduo.app.playground.entry

import android.os.Bundle
import android.util.Log
import com.kyleduo.app.playground.common.BaseActivity
import com.kyleduo.app.playground.R
import com.kyleduo.app.snippets.annotations.Snippet

class EntryActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        test()
    }

    @Snippet
    fun test() {
        val a = 100
        val b = 300
        val c = a + b
        Log.d("TAG", "test() called$c")
    }
}