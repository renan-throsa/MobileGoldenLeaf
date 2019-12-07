package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Address
import retrofit2.Call
import retrofit2.http.*

interface AddressService {

    @GET("address")
    fun get(): Call<List<Address>>

    @POST("address")
    fun save(@Body address: Address): Call<Address>

    @PUT("category/{id}")
    fun update(@Path("id") id: Long, @Body category: Address): Call<Address>
}