package com.faishal.saku.data.kategori.dana

import com.google.gson.annotations.SerializedName

data class KategoriDanaItem(

    @field:SerializedName("maximal_dana")
    val maximalDana: String,

    @field:SerializedName("id_kategori_dana")
    val idKategoriDana: String,

    @field:SerializedName("kategori_dana_nama")
    val kategoriDanaNama: String,

    @field:SerializedName("total_pengeluaran")
    val totalPengeluaran: String,

    @field:SerializedName("kategori_dana_persen")
    val kategoriDanaPersen: String
)
