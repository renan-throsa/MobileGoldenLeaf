package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Clerk
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ClerkService {


    @POST("clerk")
    fun getClerk(@Header("email_or_token") email: String, @Header("password") password: String): Call<Clerk>


}