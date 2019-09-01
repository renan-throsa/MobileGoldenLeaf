package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mo.ProductValidator
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductByIdTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import java.lang.Exception

class ProductFormPresenter(private val view: ProductFormView,
                           private val repository: ProductRepository) {

    private val validator = ProductValidator()

    fun loadBy(productid: Long) {
        val p: Product = GetProductByIdTask(productid, repository).execute().get()
        view.show(p)
    }

    fun save(product: Product): Boolean {
        return if (validator.validate(product)) {
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