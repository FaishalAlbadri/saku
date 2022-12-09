package com.faishal.saku.repository.impianku

import androidx.annotation.NonNull
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.impianku.detail.ImpiankuItem
import com.faishal.saku.data.impianku.detail.PengeluaranHariItem
import com.faishal.saku.data.scrapper.ScrapperItem
import java.io.File

interface ImpiankuDataResource {

    fun scrapper(link: String, @NonNull scrapperCallback: ScrapperCallback)

    fun impianku(idUser: String, @NonNull impiankuGetCallback: ImpiankuGetCallback)

    fun detailImpianku(idImpianku: String, @NonNull detailImpiankuGetCallback: DetailImpiankuGetCallback)

    fun addImpiankuShopee(
        idUser: String,
        title: String,
        price: String,
        img: String,
        days: String,
        link: String,
        @NonNull addImpiankuShopeeCallback: AddImpiankuShopeeCallback
    )

    fun addImpiankuManual(
        idUser: String,
        title: String,
        price: String,
        img: File,
        days: String,
        @NonNull addImpiankuManualCallback: AddImpiankuManualCallback
    )

    interface ImpiankuGetCallback {
        fun onSuccessGetImpianku(
            progressListItem: List<ImpiankuProgressItem>,
            finishedListItem: List<ImpiankuFinishedItem>,
            msg: String
        )

        fun onErrorGetImpianku(msg: String)
    }

    interface ScrapperCallback {
        fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String)
        fun onErrorScrapper(msg: String)
    }

    interface AddImpiankuShopeeCallback {
        fun onSuccessAddImpiankuShopee(msg: String)
        fun onErrorAddImpiankuShopee(msg: String)
    }

    interface AddImpiankuManualCallback {
        fun onSuccessAddImpiankuManual(msg: String)
        fun onErrorAddImpiankuManual(msg: String)
    }

    interface DetailImpiankuGetCallback {
        fun onSuccessGetDetailImpianku(
            impiankuListItem: List<ImpiankuItem>,
            pengeluaranHariListItem: List<PengeluaranHariItem>,
            msg: String
        )

        fun onErrorGetDetailImpianku(msg: String)
    }
}