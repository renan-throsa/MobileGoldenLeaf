package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product

interface CategoryListView {
    fun showProgress()
    fun hideProgress()
    fun addProduct(product: Product)
    fun showCategories(all: List<Category>)
    fun showProductsOf(category: Category)
}