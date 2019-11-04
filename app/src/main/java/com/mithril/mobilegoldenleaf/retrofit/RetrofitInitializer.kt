package com.mithril.mobilegoldenleaf.retrofit

import com.mithril.mobilegoldenleaf.retrofit.service.CategoryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val GOLDEN_LEAF_API_URL = "https://golden-leaf.herokuapp.com/api/"


    private val retrofit = Retrofit.Builder()
            .baseUrl(GOLDEN_LEAF_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun categoryService(): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }


}