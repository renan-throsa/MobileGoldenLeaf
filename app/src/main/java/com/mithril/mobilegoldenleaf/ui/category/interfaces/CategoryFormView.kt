package com.mithril.mobilegoldenleaf.ui.category.interfaces

import com.mithril.mobilegoldenleaf.models.Category
import java.util.*

interface CategoryFormView {

    fun errorSaveCategory()
    fun errorinvalidTitle()
    fun show(category: Category)
}