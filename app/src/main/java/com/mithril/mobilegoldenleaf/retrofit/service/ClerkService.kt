package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Clerk
import retrofit2.Call
import retrofit2.http.POST

interface ClerkService {

    @POST("clerk")
    fun getClerk(email: String, password: String): Call<Clerk>

    @POST("token")
    fun getToken(email: String, password: String): Call<String>

}