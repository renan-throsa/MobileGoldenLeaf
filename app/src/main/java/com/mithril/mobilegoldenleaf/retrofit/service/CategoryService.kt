package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Category
import retrofit2.Call
import retrofit2.http.*

interface CategoryService {
    @GET("category")
    fun getAll(): Call<List<Category>>

    @POST("category")
    fun save(@Body category: Category): Call<Category>


    @PUT("category/{id}")
    fun update(@Path("id") id: Long, @Body category: Category): Call<Category>
}