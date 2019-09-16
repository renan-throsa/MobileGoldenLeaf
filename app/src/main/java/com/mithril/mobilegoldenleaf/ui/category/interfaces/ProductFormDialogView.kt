package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category

interface ProductFormDialogView {
    fun productInvalidError()
    fun savingProductError()
    fun showCategory(c: Category)
}