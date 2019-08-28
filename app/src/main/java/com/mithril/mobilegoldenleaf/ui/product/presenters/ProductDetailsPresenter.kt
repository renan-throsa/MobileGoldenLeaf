package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mobilegoldenleaf.asynctask.product.GetProductById
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductDetailsView

class ProductDetailsPresenter(private val view: ProductDetailsView,
                              private val repository: ProductRepository) {


    fun loadProductDetails(id: Long) {
        val p = GetProductById(id, repository).execute().get()
        view.showProductDetails(p)
    }

}