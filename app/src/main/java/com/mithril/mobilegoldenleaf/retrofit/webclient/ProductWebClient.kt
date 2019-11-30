package com.mithril.mobilegoldenleaf.retrofit.webclient

import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.retrofit.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUISITION_NOT_SUCCEED = "Requisição não foi bem sucedida."

class ProductWebClient(private val service: ProductService = AppRetrofit().productService()) {
    private fun <T> executeRequisition(
            call: Call<T>,
            whenSuccess: (newCategories: T?) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                if (response.isSuccessful) {
                    whenSuccess(response.body())
                } else {
                    whenFail(REQUISITION_NOT_SUCCEED)
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable?) {
                whenFail(t?.message)
            }
        })
    }

    fun get(
            whenSuccess: (newCategories: List<Product>?) -> Unit
            , whenFail: (error: String?) -> Unit) {
        executeRequisition(service.getAll(), whenSuccess, whenFail)
    }

    fun get(categoryId: Long,
            whenSuccess: (newProducts: List<Product>?) -> Unit
            , whenFail: (error: String?) -> Unit) {
        executeRequisition(service.getAll(categoryId), whenSuccess, whenFail)
    }

    fun post(
            product: Product
            , whenSuccess: (newCategory: Product?) -> Unit
            , whenFail: (error: String?) -> Unit
    ) {
        executeRequisition(service.save(product), whenSuccess, whenFail)
    }


    fun put(
            id: Long,
            product: Product,
            whenSuccess: (newProduct: Product?) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        executeRequisition(service.update(id, product), whenSuccess, whenFail)
    }
}