package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.ui.category.interfaces.OnLongItemClickListener
import kotlinx.android.synthetic.main.item_category_row.view.*


class CategoryAdapter(private val context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val categories: ArrayList<Category> = ArrayList()

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


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {

            menu?.add(adapterPosition, R.id.category_list_menu_edit, Menu.NONE, R.string.edit_category)
            menu?.add(adapterPosition, R.id.category_list_menu_see_products, Menu.NONE, R.string.add_product)
            menu?.add(adapterPosition, R.id.category_list_menu_add_product, Menu.NONE, R.string.see_products)
        }

        init {
            itemView.setOnCreateContextMenuListener(this)
        }


        fun bindView(category: Category) {
            val title = itemView.item_category_title
            title.text = category.title
        }


    }
}