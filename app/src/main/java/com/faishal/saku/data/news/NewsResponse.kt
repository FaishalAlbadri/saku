package com.faishal.saku.data.news

import com.google.gson.annotations.SerializedName

data class NewsResponse(

	@field:SerializedName("news")
	val news: List<NewsItem>,

	@field:SerializedName("msg")
	val msg: String
)

