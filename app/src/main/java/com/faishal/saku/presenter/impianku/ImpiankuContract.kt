package com.faishal.saku.presenter.impianku

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.scrapper.ScrapperItem

class ImpiankuContract {

    interface impiankuView {
        fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String)
        fun onErrorScrapper(msg: String)
        fun onSuccessAddImpiankuShopee(msg: String)
        fun onErrorAddImpiankuShopee(msg: String)
    }

    interface impiankuPresenter: BasePresenter<impiankuView> {
        fun scrapper(link: String)
        fun addImpiankuShopee(idUser: String, title: String, price: String, img: String, days: String, link: String)
    }
}