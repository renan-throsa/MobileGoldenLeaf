package com.mithril.mobilegoldenleaf.retrofit.webclient

import com.mithril.mobilegoldenleaf.R
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.retrofit.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductWebClient(private val service: ProductService = AppRetrofit().productService()) {
    private fun <T> executeRequisition(
            call: Call<T>,
            whenSucceeded: (newCategories: T?) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                if (response.isSuccessful) {
                    whenSucceeded(response.body())
                } else {
                    whenFailed(R.string.product_invalid_error.toString())
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable?) {
                whenFailed(t?.message)
            }
        })
    }

    fun get(
            whenSucceeded: (newCategories: List<Product>?) -> Unit
            , whenFailed: (error: String?) -> Unit) {
        executeRequisition(service.getAll(), whenSucceeded, whenFailed)
    }

    fun get(categoryId: Long,
            whenSucceeded: (newProducts: List<Product>?) -> Unit
            , whenFailed: (error: String?) -> Unit) {
        executeRequisition(service.getAll(categoryId), whenSucceeded, whenFailed)
    }

    fun post(
            product: Product
            , whenSucceeded: (newCategory: Product?) -> Unit
            , whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.save(product), whenSucceeded, whenFailed)
    }


    fun put(
            id: Long,
            product: Product,
            whenSucceeded: (newProduct: Product?) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.update(id, product), whenSucceeded, whenFailed)
    }
}