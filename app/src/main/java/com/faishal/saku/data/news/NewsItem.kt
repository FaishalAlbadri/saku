package com.faishal.saku.data.news

import com.google.gson.annotations.SerializedName

data class NewsItem(

    @field:SerializedName("news_create")
    val newsCreate: String,

    @field:SerializedName("id_news")
    val idNews: String,

    @field:SerializedName("news_desc")
    val newsDesc: String,

    @field:SerializedName("news_judul")
    val newsJudul: String,

    @field:SerializedName("news_img")
    val newsImg: String
)
