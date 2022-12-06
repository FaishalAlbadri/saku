package com.faishal.saku.data.impianku

import com.google.gson.annotations.SerializedName

data class ImpiankuFinishedItem(
    @field:SerializedName("id_impianku")
    val idImpianku: String,

    @field:SerializedName("impianku_money_collected")
    val impiankuMoneyCollected: String,

    @field:SerializedName("impianku_update")
    val impiankuUpdate: String,

    @field:SerializedName("impianku_link_shopee")
    val impiankuLinkShopee: String,

    @field:SerializedName("impianku_title")
    val impiankuTitle: String,

    @field:SerializedName("impianku_status")
    val impiankuStatus: String,

    @field:SerializedName("impianku_price")
    val impiankuPrice: String,

    @field:SerializedName("impianku_img")
    val impiankuImg: String,

    @field:SerializedName("impianku_days")
    val impiankuDays: String,

    @field:SerializedName("impianku_create")
    val impiankuCreate: String
)
