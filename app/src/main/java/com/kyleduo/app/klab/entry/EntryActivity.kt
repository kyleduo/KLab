package com.kyleduo.app.klab.entry

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyleduo.app.klab.R
import com.kyleduo.app.klab.foundation.BaseActivity
import com.kyleduo.app.klab.m.logcat.LogcatActivity
import com.kyleduo.app.klab.m.nsd.NsdActivity
import com.kyleduo.app.klab.m.okio.OkioActivity
import com.kyleduo.app.klab.m.smoothrect.SmoothRectActivity
import com.kyleduo.app.klab.m.typeface.CustomTypeFaceActivity
import com.kyleduo.app.klab.m.window.WindowActivity
import com.kyleduo.app.klab.model.EntryItem
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
                        EntryItem("TypeFace", CustomTypeFaceActivity::class.java),
                        EntryItem("Okio", OkioActivity::class.java),
                        EntryItem("Logcat", LogcatActivity::class.java),
                        EntryItem("Window", WindowActivity::class.java),
                        EntryItem("SmoothRect", SmoothRectActivity::class.java),
                        EntryItem("NSD", NsdActivity::class.java)
                )
        )
    }
}