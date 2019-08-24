package com.mithril.mobilegoldenleaf.ui.category.dialog

import android.content.Context
import android.view.ViewGroup
import com.mithril.mobilegoldenleaf.delegate.CategoryDelegate
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.ui.product.dialog.FormCategoryDialog

class AlterCategoryDialog(viewGroup: ViewGroup, context: Context) :
        FormCategoryDialog(context, viewGroup) {

    override val positiveButtonTitle: String
        get() = "Alterar"


    fun call(category: Category, productDelegate: CategoryDelegate) {
        super.call(productDelegate)
        initializeFields(category)
    }

    private fun initializeFields(category: Category) {
        fieldTitle.setText(category.title)
    }

}