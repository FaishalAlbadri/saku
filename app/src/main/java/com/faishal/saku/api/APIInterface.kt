package com.faishal.saku.api

import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.catatan.CatatanResponse
import com.faishal.saku.data.news.NewsResponse
import retrofit2.http.POST
import com.faishal.saku.data.user.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface APIInterface {

    @FormUrlEncoded
    @POST("user/login")
    fun login(
        @Field("user_email") user_email: String,
        @Field("user_password") user_password: String
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun register(
        @Field("user_name") user_name: String,
        @Field("user_email") user_email: String,
        @Field("user_phone") user_phone: String,
        @Field("user_password") user_password: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("catatan/get")
    fun catatan(
        @Field("id_user") id_user: String
    ): Call<CatatanResponse>

    @FormUrlEncoded
    @POST("news/get")
    fun news(
        @Field("status") status: String
    ): Call<NewsResponse>


}