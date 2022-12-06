package com.faishal.saku.presenter.impianku

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.scrapper.ScrapperItem
import java.io.File

class ImpiankuContract {

    interface impiankuView {
        fun onSuccessGetImpianku(
            progressListItem: List<ImpiankuProgressItem>,
            finishedListItem: List<ImpiankuFinishedItem>,
            msg: String
        )

        fun onErrorGetImpianku(msg: String)
        fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String)
        fun onErrorScrapper(msg: String)
        fun onSuccessAddImpiankuShopee(msg: String)
        fun onErrorAddImpiankuShopee(msg: String)
        fun onSuccessAddImpiankuManual(msg: String)
        fun onErrorAddImpiankuManual(msg: String)
    }

    interface impiankuPresenter : BasePresenter<impiankuView> {
        fun impianku(idUser: String)

        fun scrapper(link: String)

        fun addImpiankuShopee(
            idUser: String,
            title: String,
            price: String,
            img: String,
            days: String,
            link: String
        )

        fun addImpiankuManual(
            idUser: String,
            title: String,
            price: String,
            img: File,
            days: String
        )
    }
}