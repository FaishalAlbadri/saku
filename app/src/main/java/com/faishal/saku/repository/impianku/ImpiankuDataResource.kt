package com.faishal.saku.repository.impianku

import androidx.annotation.NonNull
import com.faishal.saku.data.scrapper.ScrapperItem

interface ImpiankuDataResource {

    fun scrapper(link: String, @NonNull scrapperCallback: ScrapperCallback)
    fun addImpiankuShopee(idUser: String, title: String, price: String, img: String, days: String, link: String, @NonNull addImpiankuShopeeCallback: AddImpiankuShopeeCallback)

    interface ScrapperCallback {
        fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String)
        fun onErrorScrapper(msg: String)
    }

    interface AddImpiankuShopeeCallback {
        fun onSuccessAddImpiankuShopee(msg: String)
        fun onErrorAddImpiankuShopee(msg: String)
    }
}