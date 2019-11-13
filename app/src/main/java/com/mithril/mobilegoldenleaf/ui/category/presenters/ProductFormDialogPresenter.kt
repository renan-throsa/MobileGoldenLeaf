package com.mithril.mobilegoldenleaf.ui.category.presenters

import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryByIdTask
import com.mithril.mobilegoldenleaf.asynctask.category.UpdateCategoryTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.asynctask.product.UpdateProductTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.ui.category.interfaces.ProductFormDialogView
import com.mithril.mobilegoldenleaf.ui.product.validators.ProductValidator

class ProductFormDialogPresenter(private val view: ProductFormDialogView,
                                 private val repository: MobileGoldenLeafDataBase) {

    private val validator = ProductValidator()

    fun loadBy(categoryId: Long) {
        val c: Category = GetCategoryByIdTask(categoryId, repository.categoryRepository).execute().get()
        view.showCategory(c)
    }


    fun save(product: Product): Boolean {
        return if (validator.validate(product)) {
            try {
                SaveProductTask(repository.productRepository, product).execute()
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

    fun update(product: Product): Boolean {
        return if (validator.validate(product)) {
            try {
                UpdateProductTask(repository.productRepository, product).execute()
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

}