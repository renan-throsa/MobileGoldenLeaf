package com.mithril.mobilegoldenleaf.retrofit.webclient

import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.retrofit.AppRetrofit
import com.mithril.mobilegoldenleaf.retrofit.service.ClientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerWebClient(private val service: ClientService = AppRetrofit().clientService()) {
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
                    whenFailed(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                whenFailed(t.message)
            }
        })
    }

    fun get(
            whenSucceeded: (newCategories: List<Customer>?) -> Unit
            , whenFailed: (error: String?) -> Unit) {
        executeRequisition(service.get(), whenSucceeded, whenFailed)
    }

    fun get(clientId: Long,
            whenSucceeded: (newCustomer: Customer?) -> Unit
            , whenFailed: (error: String?) -> Unit) {
        executeRequisition(service.get(clientId), whenSucceeded, whenFailed)
    }

    fun post(
            customer: Customer
            , whenSucceeded: (newCustomer: Customer?) -> Unit
            , whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.post(customer), whenSucceeded, whenFailed)
    }


    fun put(
            id: Long,
            customer: Customer,
            whenSucceeded: (newCustomer: Customer?) -> Unit,
            whenFailed: (error: String?) -> Unit
    ) {
        executeRequisition(service.put(id, customer), whenSucceeded, whenFailed)
    }
}