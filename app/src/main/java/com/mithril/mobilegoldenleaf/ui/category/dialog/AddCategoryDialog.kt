package com.mithril.mobilegoldenleaf.ui.category.dialog

import android.content.Context
import android.view.ViewGroup
import com.mithril.mobilegoldenleaf.ui.product.dialog.FormCategoryDialog

class AddCategoryDialog(viewGroup: ViewGroup, private val context: Context) :
        FormCategoryDialog(context, viewGroup) {
    override val positiveButtonTitle: String
        get() = "Salvar"
}