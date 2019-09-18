package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mobilegoldenleaf.asynctask.product.GetProductByIdTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductsFromCategoryTask
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.ProductRepository
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductListView

class ProductListPresenter(private val view: ProductListView,
                           private val repository: ProductRepository) {

    fun searchProducts(term: String) {
        if (term.isEmpty()) {
            view.showProducts(GetProductTask(repository).execute().get())
        } else {
            view.showProducts(repository.search(term))
        }
    }

    fun searchProductsWith(categoryId: Long) {
        view.showProducts(GetProductsFromCategoryTask(categoryId, repository).execute().get())
    }

    fun showProductDetails(product: Product) {
        view.showProductDetails(GetProductByIdTask(product.id, repository).execute().get())
    }

}