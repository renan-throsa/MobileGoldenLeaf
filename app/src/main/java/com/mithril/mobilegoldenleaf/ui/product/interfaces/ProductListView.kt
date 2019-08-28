package com.mithril.mobilegoldenleaf.ui.product.interfaces

import com.mithril.mobilegoldenleaf.models.Product

interface ProductListView {
    fun showProgress()
    fun hideProgress()
    fun showProducts(all: List<Product>)
    fun showProductDetails(product: Product)
}