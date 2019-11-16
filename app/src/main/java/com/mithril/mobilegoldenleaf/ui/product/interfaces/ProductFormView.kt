package com.mithril.mobilegoldenleaf.ui.product.interfaces

import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product

interface ProductFormView {
    fun show(product: Product)
    fun productInvalidError()
    fun savingProductError()
    fun showCategories(c: List<Category>)

}