package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val context: Context, private val products: List<Product>) :
        BaseAdapter() {

    override fun getCount(): Int = products.size


    override fun getItem(position: Int): Product = products[position]


    override fun getItemId(i: Int): Long = products[i].id.toLong()


    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup): View {
        val product = products[position]
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

        boundInformation(holder, product)
        return row
    }

    private fun getInflate(viewGroup: ViewGroup): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_product, viewGroup, false)
    }

    private fun boundInformation(row: ViewHolder, p: Product) {
        row.brand.text = p.brand
        row.description.text = p.description
        row.unit_cost.text = p.unitCost.toString()
        row.code.text = p.code
    }

    fun update() {
        notifyDataSetChanged()
    }

    companion object {
        data class ViewHolder(val view: View) {
            val brand: TextView = view.item_product_brand
            val description: TextView = view.item_product_description
            val unit_cost: TextView = view.item_product_value
            val code: TextView = view.item_product_code
        }
    }


}

