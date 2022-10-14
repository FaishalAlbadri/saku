package com.faishal.saku.api

import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.catatan.CatatanResponse
import com.faishal.saku.data.kategori.dana.KategoriDanaResponse
import com.faishal.saku.data.news.NewsResponse
import com.faishal.saku.data.pengeluaran.PengeluaranHariResponse
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
    @POST("catatan/add")
    fun catatanAdd(
        @Field("id_user") id_user: String,
        @Field("catatan_pendapatan") catatan_pendapatan: String,
        @Field("catatan_waktu") catatan_waktu: String
    ): Call<CatatanResponse>

    @FormUrlEncoded
    @POST("news/get")
    fun news(
        @Field("status") status: String
    ): Call<NewsResponse>

    @FormUrlEncoded
    @POST("pengeluaran/saran")
    fun kategoriDana(
        @Field("id_user") id_user: String,
        @Field("id_catatan") id_catatan: String
    ): Call<KategoriDanaResponse>

    @FormUrlEncoded
    @POST("pengeluaran/getByDate")
    fun pengeluaran(
        @Field("id_user") id_user: String,
        @Field("id_catatan") id_catatan: String
    ): Call<PengeluaranHariResponse>

}