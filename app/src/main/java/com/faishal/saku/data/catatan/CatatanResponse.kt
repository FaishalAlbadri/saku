package com.faishal.saku.data.catatan

import com.google.gson.annotations.SerializedName

data class CatatanResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("catatan")
	val catatan: List<CatatanItem>,

	@field:SerializedName("user")
	val user: List<UserItem>
)