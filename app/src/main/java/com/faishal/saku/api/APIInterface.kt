package com.faishal.saku.api

import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.catatan.CatatanResponse
import com.faishal.saku.data.kategori.dana.KategoriDanaResponse
import com.faishal.saku.data.news.NewsResponse
import com.faishal.saku.data.pengeluaran.PengeluaranHariResponse
import com.faishal.saku.data.scrapper.ScrapperResponse
import retrofit2.http.POST
import com.faishal.saku.data.user.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface APIInterface {

    @FormUrlEncoded
    @POST("user/profile")
    fun profile(
        @Field("id_user") id_user: String,
    ): Call<UserResponse>

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
    @POST("pengeluaran/get")
    fun pengeluaran(
        @Field("id_user") id_user: String,
        @Field("id_catatan") id_catatan: String
    ): Call<PengeluaranHariResponse>

    @FormUrlEncoded
    @POST("pengeluaran/add")
    fun pengeluaranAdd(
        @Field("id_user") id_user: String,
        @Field("id_catatan") id_catatan: String,
        @Field("id_kategori_dana") id_kategori_dana: String,
        @Field("id_kategori_pokok") id_kategori_pokok: String,
        @Field("catatan_item_desc") catatan_item_desc: String,
        @Field("catatan_item_harga") catatan_item_harga: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("pengeluaran/edit")
    fun pengeluaranEdit(
        @Field("id_catatan_item") id_catatan_item: String,
        @Field("catatan_item_desc") catatan_item_desc: String,
        @Field("catatan_item_harga") catatan_item_harga: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("pengeluaran/delete")
    fun pengeluaranDelete(
        @Field("id_catatan_item") id_catatan_item: String
    ): Call<BaseResponse>

    @FormUrlEncoded
    @POST("impianku/shoope-produk")
    fun scrapperShoope(
        @Field("link") link: String
    ): Call<ScrapperResponse>

    @FormUrlEncoded
    @POST("impianku/add-shopee")
    fun addImpiankuShopee(
        @Field("id_user") id_user: String,
        @Field("impianku_title") impianku_title: String,
        @Field("impianku_price") impianku_price: String,
        @Field("impianku_img") impianku_img: String,
        @Field("impianku_days") impianku_days: String,
        @Field("impianku_link_shopee") impianku_link_shopee: String
    ): Call<BaseResponse>

}