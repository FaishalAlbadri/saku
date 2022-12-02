package com.faishal.saku.repository.impianku

import androidx.annotation.NonNull
import com.faishal.saku.data.scrapper.ScrapperItem

interface ImpiankuDataResource {

    fun scrapper(link: String, @NonNull scrapperCallback: ScrapperCallback)

    interface ScrapperCallback {
        fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String)
        fun onErrorScrapper(msg: String)
    }
}