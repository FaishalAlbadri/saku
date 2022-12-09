package com.faishal.saku.data.impianku.detail

import com.google.gson.annotations.SerializedName

data class PengeluaranHariItem(

	@field:SerializedName("pengeluaran")
	val pengeluaran: List<PengeluaranItem>,

	@field:SerializedName("catatan_item_create")
	val catatanItemCreate: String
)
