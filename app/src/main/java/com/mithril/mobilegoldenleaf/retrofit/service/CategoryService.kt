package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {
    @GET("category")
    fun getAll(): Call<List<Category>>
}