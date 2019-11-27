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
            whenSuccess: (newCategories: List<Category>?) -> Unit
            , whenFail: (error: String?) -> Unit) {
        executeRequisition(service.getAll(), whenSuccess, whenFail)
    }


    fun post(
            category: Category
            , whenSuccess: (newCategory: Category?) -> Unit
            , whenFail: (error: String?) -> Unit
    ) {
        executeRequisition(service.save(category), whenSuccess, whenFail)
    }


    fun put(
            id: Long,
            category: Category,
            whenSuccess: (newCategory: Category?) -> Unit,
            whenFail: (error: String?) -> Unit
    ) {
        executeRequisition(service.update(id, category), whenSuccess, whenFail)
    }

}