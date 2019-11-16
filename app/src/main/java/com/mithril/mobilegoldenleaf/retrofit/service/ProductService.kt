package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductService {
    @GET("product")
    fun getAll(): Call<List<Product>>

    @POST("product")
    fun save(@Body product: Product): Call<Product>


    @PUT("product/{id}")
    fun update(@Path("id") id: Long, @Body category: Product): Call<Product>
}