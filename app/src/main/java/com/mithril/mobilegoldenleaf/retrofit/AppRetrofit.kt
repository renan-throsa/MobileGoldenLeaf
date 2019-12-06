package com.mithril.mobilegoldenleaf.retrofit

import com.mithril.mobilegoldenleaf.retrofit.service.ClientService
import com.mithril.mobilegoldenleaf.retrofit.service.CategoryService
import com.mithril.mobilegoldenleaf.retrofit.service.ClerkService
import com.mithril.mobilegoldenleaf.retrofit.service.ProductService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppRetrofit {

    private val BASE_URL = "https://golden-leaf.herokuapp.com/api/"


    private val client by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }


    fun categoryService(): CategoryService {
        return retrofit.create(CategoryService::class.java)
    }

    fun productService(): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    fun clerkService(): ClerkService {
        return retrofit.create(ClerkService::class.java)
    }

    fun clientService(): ClientService {
        return retrofit.create(ClientService::class.java)
    }


}