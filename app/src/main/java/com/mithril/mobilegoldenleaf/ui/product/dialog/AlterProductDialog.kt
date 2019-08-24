package com.mithril.mobilegoldenleaf.ui.product.dialog

import android.content.Context
import android.view.ViewGroup
import com.mithril.mobilegoldenleaf.delegate.CategoryDelegate
import com.mithril.mobilegoldenleaf.delegate.ProductDelegate
import com.mithril.mobilegoldenleaf.models.Product

class AlterProductDialog(viewGroup: ViewGroup, private val context: Context) :
        FormProductDialog(context, viewGroup) {

    override val positiveButtonTitle: String
        get() = "Alterar"


    fun call(product: Product, productDelegate: ProductDelegate) {
        super.call(productDelegate)
        initializeFields(product)
    }

    private fun initializeFields(product: Product) {
        fieldValue.setText(product.unitCost.toString())
        fieldBrand.setText(product.brand)
        fieldDescription.setText(product.description)
        fieldCode.setText(product.code)
    }

}