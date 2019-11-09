package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Clerk
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClerkService {

    @POST("clerk")
    fun getClerk(@Body email: String, @Body password: String): Call<Clerk>

    @POST("token")
    fun getToken(@Body email: String, @Body password: String): Call<String>

}