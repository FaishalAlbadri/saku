package com.faishal.saku.data.catatan

import com.google.gson.annotations.SerializedName

data class CatatanItem(

    @field:SerializedName("catatan_waktu")
    val catatanWaktu: String,

    @field:SerializedName("catatan_pendapatan")
    val catatanPendapatan: String,

    @field:SerializedName("id_catatan")
    val idCatatan: String,

    @field:SerializedName("id_user")
    val idUser: String,

    @field:SerializedName("total_pengeluaran")
    val totalPengeluaran: String
)