package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Category
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryService {
    @GET("category")
    fun getAll(): Call<List<Category>>

    @POST("category")
    fun save(@Body category: Category): Call<Category>
}