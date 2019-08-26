package com.mithril.mobilegoldenleaf.ui.product.interfaces

import com.mithril.mobilegoldenleaf.models.Product

interface ProductFormView {
    fun show(product: Product)
    fun errorinvalidProduct()
    fun errorSaveProduct()
}