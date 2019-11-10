package com.mithril.mobilegoldenleaf.retrofit.service

import com.mithril.mobilegoldenleaf.models.Clerk
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ClerkService {

    @POST("clerk")
    fun getClerk(@Field("email") email: String, @Field("password") password: String): Call<Clerk>


    @POST("token")
    fun getToken(@Field("email") email: String, @Field("password") password: String): Call<String>

}