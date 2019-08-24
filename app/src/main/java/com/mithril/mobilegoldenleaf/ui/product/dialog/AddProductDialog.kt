package com.mithril.mobilegoldenleaf.ui.product.dialog

import android.content.Context
import android.view.ViewGroup

class AddProductDialog(viewGroup: ViewGroup, context: Context) :
        FormProductDialog(context, viewGroup) {
    override val positiveButtonTitle: String
        get() = "Salvar"
}