package com.faishal.saku.repository.impianku

class ImpiankuRepository: ImpiankuDataResource {

    private var impiankuDataResource: ImpiankuDataResource

    constructor(impiankuDataResource: ImpiankuDataResource) {
        this.impiankuDataResource = impiankuDataResource
    }

    override fun scrapper(link: String, scrapperCallback: ImpiankuDataResource.ScrapperCallback) {
        impiankuDataResource.scrapper(link, scrapperCallback)
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
}