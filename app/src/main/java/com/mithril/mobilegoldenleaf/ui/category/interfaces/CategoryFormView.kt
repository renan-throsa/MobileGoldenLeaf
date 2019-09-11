package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category
import java.util.*

interface CategoryFormView {

    fun showSavingCategoryError()
    fun showInvalidTitleError()
    fun show(category: Category)
}