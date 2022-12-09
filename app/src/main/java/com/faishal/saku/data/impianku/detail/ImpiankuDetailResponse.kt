package com.faishal.saku.data.impianku.detail

import com.google.gson.annotations.SerializedName

data class ImpiankuDetailResponse(

    @field:SerializedName("msg")
    val msg: String,

    @field:SerializedName("impianku")
    val impianku: List<ImpiankuItem>,

    @field:SerializedName("pengeluaran_hari")
    val pengeluaranHari: List<PengeluaranHariItem>
)