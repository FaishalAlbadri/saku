package com.faishal.saku.presenter.impianku

import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.repository.impianku.ImpiankuDataResource
import com.faishal.saku.repository.impianku.ImpiankuRepository

class ImpiankuPresenter: ImpiankuContract.impiankuPresenter {

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

    override fun onAttachView(view: ImpiankuContract.impiankuView) {
        impiankuView = view
    }

    override fun onDettachView() {}
}