package com.faishal.saku.data.kategori.dana

import com.google.gson.annotations.SerializedName

data class KategoriDanaResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("kategori_dana")
	val kategoriDana: List<KategoriDanaItem>
)