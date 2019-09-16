package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category

interface CategoryFormView {

    fun showSavingCategoryError()
    fun showInvalidTitleError()
    fun show(category: Category)
}