package com.faishal.saku.presenter.impianku

import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.repository.impianku.ImpiankuDataResource
import com.faishal.saku.repository.impianku.ImpiankuRepository

class ImpiankuPresenter : ImpiankuContract.impiankuPresenter {

    private var impiankuRepository: ImpiankuRepository
    private lateinit var impiankuView: ImpiankuContract.impiankuView

    constructor(impiankuRepository: ImpiankuRepository) {
        this.impiankuRepository = impiankuRepository
    }


    override fun scrapper(link: String) {
        impiankuRepository.scrapper(link, object : ImpiankuDataResource.ScrapperCallback {
            override fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String) {
                impiankuView.onSuccessScrapper(scrapperListItem, msg)
            }

            override fun onErrorScrapper(msg: String) {
                impiankuView.onErrorScrapper(msg)
            }

        })
    }

    override fun addImpiankuShopee(
        idUser: String,
        title: String,
        price: String,
        img: String,
        days: String,
        link: String
    ) {
        impiankuRepository.addImpiankuShopee(
            idUser,
            title,
            price,
            img,
            days,
            link,
            object : ImpiankuDataResource.AddImpiankuShopeeCallback {
                override fun onSuccessAddImpiankuShopee(msg: String) {
                    impiankuView.onSuccessAddImpiankuShopee(msg)
                }

                override fun onErrorAddImpiankuShopee(msg: String) {
                    impiankuView.onErrorAddImpiankuShopee(msg)
                }

            })
    }

    override fun onAttachView(view: ImpiankuContract.impiankuView) {
        impiankuView = view
    }

    override fun onDettachView() {}
}