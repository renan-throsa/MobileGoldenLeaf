package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.extentions.toBrazilianFormat
import com.mithril.mobilegoldenleaf.models.Product
import kotlinx.android.synthetic.main.item_product_row.view.*

class ProductAdapter(private val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val products: ArrayList<Product> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bindView(product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun getItemId(i: Int): Long = products[i].id

    fun getItem(position: Int): Product = products[position]


    fun update(all: List<Product>) {
        notifyItemRangeRemoved(0, this.products.size)
        products.clear()
        products.addAll(all)
        notifyItemRangeInserted(0, this.products.size)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.add(adapterPosition, R.id.product_list_menu_edit, Menu.NONE, R.string.edit_product)
        }

        init {
            itemView.setOnCreateContextMenuListener(this)
        }


        fun bindView(product: Product) {
            itemView.item_product_brand.text = product.brand
            itemView.item_product_description.text = product.description
            itemView.item_product_value.text = product.unitCost.toBrazilianFormat()
            itemView.item_product_code.text = product.code
        }


    }

}

