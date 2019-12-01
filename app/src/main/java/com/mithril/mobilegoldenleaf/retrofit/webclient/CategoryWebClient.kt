package com.mithril.mobilegoldenleaf.retrofit.webclient

import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.retrofit.service.CategoryService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUISITION_NOT_SUCCEED = "Requisição não foi bem sucedida."

class CategoryWebClient(private val service: CategoryService = AppRetrofit().categoryService()) {

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
                    whenFailed(REQUISITION_NOT_SUCCEED)
                }
            }
            override fun onFailure(call: Call<T?>, t: Throwable?) {
                whenFailed(t?.message)
            }
        })
    }

    fun get(
            whenSucceeded: (newCategories: List<Category>?) -> Unit
            , whenFailed: (error: String?) -> Unit) {
        executeRequisition(service.getAll(), whenSucceeded, whenFailed)
    }


    fun post(
            category: Category
            , whenSucceeded: (newCategory: Category?) -> Unit
            , whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.save(category), whenSucceeded, whenFailed)
    }


    fun put(
            id: Long,
            category: Category,
            whenSucceeded: (newCategory: Category?) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.update(id, category), whenSucceeded, whenFailed)
    }

}