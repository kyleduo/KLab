package com.kyleduo.app.playground.entry

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyleduo.app.playground.R
import com.kyleduo.app.playground.common.BaseActivity
import com.kyleduo.app.playground.m.typeface.CustomTypeFaceActivity
import com.kyleduo.app.playground.model.EntryItem
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity : BaseActivity() {

    private val adapter by lazy { EntryListAdapter() }
    private val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        entryList.adapter = adapter
        entryList.layoutManager = layoutManager

        adapter.replaceEntryItems(
            listOf(
                EntryItem("TypeFace", CustomTypeFaceActivity::class.java)
            )
        )
    }
}