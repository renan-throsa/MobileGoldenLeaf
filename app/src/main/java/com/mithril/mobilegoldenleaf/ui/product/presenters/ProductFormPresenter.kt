package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryLocallyTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductByIdTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.UpdateProductTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.validators.ProductValidator

class ProductFormPresenter(private val view: ProductFormView,
                           private val repository: MobileGoldenLeafDataBase) {

    private val validator = ProductValidator()

    fun loadBy(productId: Long) {
        val p: Product = GetProductByIdTask(productId, repository.productRepository).execute().get()
        view.showProduct(p)
    }

    fun save(product: Product): Boolean {
        return if (validator.validate(product)) {
            try {
                if (product.id != 0L) {
                    UpdateProductTask(repository.productRepository, product).execute()
                } else {
                    SaveProductTask(repository.productRepository, product).execute()
                }
                true
            } catch (e: Exception) {
                view.savingProductError()
                false
            }
        } else {
            view.productInvalidError()
            false
        }
    }


    fun loadCategories() {
        val c: List<Category> = GetCategoryLocallyTask(repository.categoryRepository).execute().get()
        view.showCategories(c)

    }
}