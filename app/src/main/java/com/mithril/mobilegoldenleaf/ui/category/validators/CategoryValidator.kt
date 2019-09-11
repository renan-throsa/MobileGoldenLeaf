package com.mithril.mobilegoldenleaf.ui.category.validators

import com.mithril.mobilegoldenleaf.models.Category

class CategoryValidator {

    fun validate(c: Category) = checkTitle(c.title)

    private fun checkTitle(description: String?) = description?.length in 3..20

}