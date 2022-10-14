package com.faishal.saku.data.pengeluaran

import com.google.gson.annotations.SerializedName

data class PengeluaranHariResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("pengeluaran_hari")
	val pengeluaranHari: List<PengeluaranHariItem>
)