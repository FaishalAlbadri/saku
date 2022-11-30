package com.faishal.saku.data.scrapper

import com.google.gson.annotations.SerializedName

data class ScrapperItem(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("harga")
    val harga: Int,

    @field:SerializedName("title")
    val title: String
)
