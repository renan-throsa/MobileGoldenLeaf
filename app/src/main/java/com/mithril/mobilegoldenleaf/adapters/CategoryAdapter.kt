package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import kotlinx.android.synthetic.main.item_category_row.view.*

class CategoryAdapter(private val context: Context) : BaseAdapter() {

    private val categories: ArrayList<Category> = ArrayList()

    override fun getCount(): Int = categories.size


    override fun getItem(position: Int): Category = categories[position]


    override fun getItemId(i: Int): Long = categories[i].id


    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        val category = categories[position]
        val holder: ViewHolder
        val row: View
        if (convertView == null) {
            row = getInflate(viewGroup)
            holder = ViewHolder(row)
            row.tag = holder
        } else {
            row = convertView
            holder = convertView.tag as ViewHolder
        }

        boundInformation(holder, category)
        return row
    }

    private fun getInflate(viewGroup: ViewGroup): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_category_row, viewGroup, false)
    }

    private fun boundInformation(row: ViewHolder, c: Category) {
        row.title.text = c.title

    }

    fun update(all: List<Category>) {
        categories.clear()
        categories.addAll(all)
        notifyDataSetChanged()
    }

    companion object {
        data class ViewHolder(val view: View) {
            val title: TextView = view.item_category_title
        }
    }

}
