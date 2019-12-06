package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Customer
import retrofit2.Call
import retrofit2.http.*

interface ClientService {

    @GET("client")
    fun get(): Call<List<Customer>>

    @GET("client/{id}")
    fun get(@Path("id") id: Long): Call<Customer>

    @POST("customer")
    fun post(@Body customer: Customer): Call<Customer>

    @PUT("customer/{id}")
    fun put(@Path("id") id: Long, @Body customer: Customer): Call<Customer>
}