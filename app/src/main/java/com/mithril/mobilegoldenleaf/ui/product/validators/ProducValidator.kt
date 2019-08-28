package com.mithril.mo

import com.mithril.mobilegoldenleaf.models.Product
import java.math.BigDecimal

class ProducValidator {
    fun validade(p: Product) = with(p) {
        checkDescription(description) && checkCode(code) && checkValue("1.50")
    }

    private fun checkDescription(description: String?) = description?.length in 3..20
    private fun checkCode(code: String?) = code?.length in 9..13

    private fun checkValue(valueToText: String): Boolean {
        try {
            BigDecimal(valueToText)
            return true
        } catch (exception: NumberFormatException) {
            BigDecimal.ZERO
            return false
        }
    }
}