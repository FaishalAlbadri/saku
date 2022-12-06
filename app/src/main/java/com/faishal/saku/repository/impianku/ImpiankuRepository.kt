package com.faishal.saku.repository.impianku

import java.io.File

class ImpiankuRepository: ImpiankuDataResource {

    private var impiankuDataResource: ImpiankuDataResource

    constructor(impiankuDataResource: ImpiankuDataResource) {
        this.impiankuDataResource = impiankuDataResource
    }

    override fun scrapper(link: String, scrapperCallback: ImpiankuDataResource.ScrapperCallback) {
        impiankuDataResource.scrapper(link, scrapperCallback)
    }

    override fun impianku(
        idUser: String,
        impiankuGetCallback: ImpiankuDataResource.ImpiankuGetCallback
    ) {
        impiankuDataResource.impianku(idUser, impiankuGetCallback)
    }

    override fun addImpiankuShopee(
        idUser: String,
        title: String,
        price: String,
        img: String,
        days: String,
        link: String,
        addImpiankuShopeeCallback: ImpiankuDataResource.AddImpiankuShopeeCallback
    ) {
        impiankuDataResource.addImpiankuShopee(idUser, title, price, img, days, link, addImpiankuShopeeCallback)
    }

    override fun addImpiankuManual(
        idUser: String,
        title: String,
        price: String,
        img: File,
        days: String,
        addImpiankuManualCallback: ImpiankuDataResource.AddImpiankuManualCallback
    ) {
        impiankuDataResource.addImpiankuManual(idUser, title, price, img, days, addImpiankuManualCallback)
    }
}