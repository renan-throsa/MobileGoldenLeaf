package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category

interface CategoryListView {
    fun showProgress()
    fun hideProgress()
    fun showCategories(all: List<Category>)
    fun showProductsOf(category: Category)
    fun gettingCategoriesError()
}