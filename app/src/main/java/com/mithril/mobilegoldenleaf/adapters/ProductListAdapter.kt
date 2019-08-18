package com.mithril.mobilegoldenleaf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Product

import java.util.ArrayList

class ProductListAdapter(private val context: Context) : BaseAdapter() {

    private val products = ArrayList<Product>()

    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(position: Int): Product {
        return products[position]
    }

    override fun getItemId(i: Int): Long {
        return products[i].id.toLong()
    }

    override fun getView(position: Int, view: View, viewGroup: ViewGroup): View {
        val createdView = getInflate(viewGroup)
        val p = products[position]
        boundInformation(createdView, p)
        return createdView
    }

    private fun getInflate(viewGroup: ViewGroup): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_product, viewGroup, false)
    }

    private fun boundInformation(createdView: View, p: Product) {
        val brand = createdView.findViewById<TextView>(R.id.item_product_brand)
        brand.text = p.brand

        val description = createdView.findViewById<TextView>(R.id.item_product_description)
        description.text = p.description

        val value = createdView.findViewById<TextView>(R.id.item_product_value)
        value.text = p.unit_cost.toString()

        val code = createdView.findViewById<TextView>(R.id.item_product_code)
        code.text = p.code
    }

    fun update(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()
    }
}

