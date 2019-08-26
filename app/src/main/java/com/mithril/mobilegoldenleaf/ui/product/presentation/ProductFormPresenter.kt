package com.mithril.mobilegoldenleaf.ui.product.presentation

import com.mithril.mo.ProducValidator
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import java.lang.Exception

class ProductFormPresenter(private val view: ProductFormView,
                           private val repository: ProductRepository) {

    private val validator = ProducValidator()

    fun loadBy(productid: Long) {
        val p: Product = repository.get(productid)
        view.show(p)
    }

    fun save(p: Product): Boolean {
        return if (validator.validade(p)) {
            try {
                repository.save(p)
                true
            } catch (e: Exception) {
                view.errorSaveProduct()
                false
            }
        } else {
            view.errorinvalidProduct()
            false
        }
    }
}