package com.kyleduo.app.klab.entry

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kyleduo.app.klab.R
import com.kyleduo.app.klab.foundation.KLabApp
import com.kyleduo.app.klab.model.EntryItem

/**
 * @author kyleduo on 3/2/21
 */
class EntryListAdapter : RecyclerView.Adapter<EntryItemViewHolder>() {

    private val entryItems: MutableList<EntryItem> = mutableListOf()

    fun replaceEntryItems(items: List<EntryItem>) {
        entryItems.clear()
        entryItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryItemViewHolder {
        return EntryItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_entry_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return entryItems.size
    }

    override fun onBindViewHolder(holder: EntryItemViewHolder, position: Int) {
        holder.bind(entryItems[position])
    }
}

class EntryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameTv: TextView = itemView.findViewById(R.id.entryItemName)

    fun bind(entryItem: EntryItem) {
        nameTv.text = entryItem.name
        itemView.setOnClickListener {
            KLabApp.app.startActivity(Intent(KLabApp.app, entryItem.target).also {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        }
    }
}
