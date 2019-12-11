package com.mithril.mobilegoldenleaf.ui.product

import androidx.lifecycle.ViewModel
import com.mithril.mobilegoldenleaf.models.Product

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    fun get(whenSucceeded: (newProducts: List<Product>) -> Unit,
            whenFailed: (error: String?) -> Unit) {
        repository.get(whenSucceeded, whenFailed)
    }

    fun get(categoryId: Long, whenSucceeded: (newProducts: List<Product>) -> Unit,
            whenFailed: (error: String?) -> Unit) {
        repository.get(categoryId, whenSucceeded, whenFailed)
    }
}