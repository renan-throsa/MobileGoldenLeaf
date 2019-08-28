package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mo.ProducValidator
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductById
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import java.lang.Exception

class ProductFormPresenter(private val view: ProductFormView,
                           private val repository: ProductRepository) {

    private val validator = ProducValidator()

    fun loadBy(productid: Long) {
        val p: Product = GetProductById(productid, repository).execute().get()
        view.show(p)
    }

    fun save(product: Product): Boolean {
        return if (validator.validade(product)) {
            try {
                SaveProductTask(repository, product)
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