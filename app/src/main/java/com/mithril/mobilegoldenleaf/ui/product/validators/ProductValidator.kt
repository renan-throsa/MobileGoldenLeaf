package com.mithril.mobilegoldenleaf.ui.product.validators

import com.mithril.mobilegoldenleaf.models.Product

class ProductValidator {
    fun validate(p: Product) = with(p) {
        checkDescription(description) && checkCode(code)
    }

    private fun checkDescription(description: String?) = description?.length in 3..30
    private fun checkCode(code: String?) = code?.length in 9..13

}