package com.faishal.saku.data.impianku.detail

import com.google.gson.annotations.SerializedName

data class PengeluaranItem(

	@field:SerializedName("id_catatan_item")
	val idCatatanItem: String,

	@field:SerializedName("kategori_dana_nama")
	val kategoriDanaNama: String,

	@field:SerializedName("catatan_item_desc")
	val catatanItemDesc: String,

	@field:SerializedName("catatan_item_harga")
	val catatanItemHarga: String,

	@field:SerializedName("catatan_item_create")
	val catatanItemCreate: String
)