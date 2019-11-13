package com.mithril.mobilegoldenleaf.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import kotlinx.android.synthetic.main.item_category_row.view.*


class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val categories: ArrayList<Category> = ArrayList()
    private lateinit var onItemClickListener: OnItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_category_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun getItemId(i: Int): Long = categories[i].id


    fun getItem(position: Int): Category = categories[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bindView(category)
    }


    fun update(all: List<Category>) {
        notifyItemRangeRemoved(0, this.categories.size)
        categories.clear()
        categories.addAll(all)
        notifyItemRangeInserted(0, this.categories.size)
    }


    fun add(category: Category) {
        val currentSize = this.categories.size
        categories.add(category)
        val newSize = this.categories.size
        notifyItemRangeInserted(currentSize, newSize)
    }

    fun edit(position: Int, category: Category) {
        categories[position] = category
        notifyItemChanged(position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(category: Category) {
            val title = itemView.item_category_title
            title.text = category.title
        }

        private fun configureContextMenu(itemView: View) {
            itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->

            }

        }

        private fun configureItemClick(itemView: View) {

        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, category: Category)
    }
}