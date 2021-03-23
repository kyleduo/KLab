package com.kyleduo.app.klab.m.logcat

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewDelegate
import com.drakeet.multitype.MultiTypeAdapter
import com.kyleduo.app.klab.foundation.BaseActivity
import kotlinx.android.synthetic.main.activity_logcat.*
import java.util.*


/**
 * @author kyleduo on 3/20/21
 */
class LogcatActivity : BaseActivity(), Callback {

    private var viewer: LogcatViewer? = null
    private val lines = LinkedList<String>()
    private val adapter = MultiTypeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_logcat)

        adapter.register(object : ItemViewDelegate<String, LogItemViewHolder>() {
            override fun onBindViewHolder(holder: LogItemViewHolder, item: String) {
                holder.text.text = item
            }

            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup
            ): LogItemViewHolder {
                return LogItemViewHolder(TextView(this@LogcatActivity).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                })
            }
        })
        outputList.adapter = adapter
        outputList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        outputList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            private val paint by lazy {
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.GRAY
                }
            }

            override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                super.onDrawOver(c, parent, state)
                val childCount: Int = parent.childCount
                val left: Int = parent.paddingLeft
                val right: Int = parent.width - parent.paddingRight

                for (i in 0 until childCount - 1) {
                    val view: View = parent.getChildAt(i)
                    val top = view.bottom.toFloat()
                    val bottom = view.bottom.toFloat() + 2
                    c.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
                }
            }

            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.bottom += 2
            }
        })

        adapter.items = lines
    }

    override fun onItem(content: String) {
        lines.add(0, content)
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        viewer = LogcatViewer(this).also {
            it.start()
        }
    }

    override fun onStop() {
        super.onStop()
        viewer?.interrupt()
    }


    class LogItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text = itemView as TextView
    }
}
