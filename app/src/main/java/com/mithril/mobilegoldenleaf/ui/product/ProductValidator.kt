package com.mithril.mobilegoldenleaf.ui.product

import com.mithril.mobilegoldenleaf.models.Product
import java.math.BigDecimal

class ProductValidator {

    var error = "Everything is ok."

    fun validate(p: Product): Boolean {

        with(p) {
            if (!checkBrand(brand)) {
                error = "Erro. A marca precisa ter entre 3 e 25 caracteres."
                return false
            }
            if (!checkDescription(description)) {
                error = "Erro. A descrição precisa ter entre 3 e 50 caracteres."
                return false
            }

            if (!checkCode(code)) {
                error = "Erro. O código precisa ter entre 9 e 13 caracteres."
                return false
            }

            if (!checkPrice(unitCost)) {
                error = "Erro. O preço precisa ser maior que 0."
                return false
            }
            return true
        }
    }

    private fun checkBrand(brand: String?) = brand?.length in 3..25
    private fun checkDescription(description: String?) = description?.length in 3..50
    private fun checkCode(code: String?) = code?.length in 9..13
    private fun checkPrice(price: BigDecimal) = price > BigDecimal.ZERO
}