package com.mithril.mobilegoldenleaf.ui.product.presenters

import com.mithril.mobilegoldenleaf.asynctask.category.GetCategoryLocallyTask
import com.mithril.mobilegoldenleaf.asynctask.product.GetProductByIdTask
import com.mithril.mobilegoldenleaf.asynctask.product.SaveProductTask
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.AppDataBase
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.ui.product.interfaces.ProductFormView
import com.mithril.mobilegoldenleaf.ui.product.validators.ProductValidator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFormPresenter(private val view: ProductFormView,
                           private val repository: AppDataBase) {

    private val validator = ProductValidator()

    fun loadBy(productId: Long) {
        val p: Product = GetProductByIdTask(productId, repository.productRepository).execute().get()
        view.show(p)
    }


    fun save(product: Product): Boolean {
        return if (validator.validate(product)) {
            val service = AppRetrofit().productService()
            val call =
                    if (product.id != 0L) {
                        service.update(product.id, product)
                    } else {
                        service.save(product)
                    }
            call.enqueue(object : Callback<Product?> {
                override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
                    response.body()?.let {
                    }
                }

                override fun onFailure(call: Call<Product?>, t: Throwable) {
                    view.savingProductError()
                }
            })

            SaveProductTask(repository.productRepository, product).execute()
            true
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