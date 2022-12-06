package com.faishal.saku.data.impianku

import com.google.gson.annotations.SerializedName

data class ImpiankuResponse(

	@field:SerializedName("impianku_finished")
	val impiankuFinished: List<ImpiankuFinishedItem>,

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("impianku_progress")
	val impiankuProgress: List<ImpiankuProgressItem>
)
