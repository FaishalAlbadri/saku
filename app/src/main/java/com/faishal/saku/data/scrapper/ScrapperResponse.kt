package com.faishal.saku.data.scrapper

import com.google.gson.annotations.SerializedName

data class ScrapperResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("scrapper")
	val scrapperItem: List<ScrapperItem>
)


