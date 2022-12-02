package com.faishal.saku.repository.impianku

class ImpiankuRepository: ImpiankuDataResource {

    private var impiankuDataResource: ImpiankuDataResource

    constructor(impiankuDataResource: ImpiankuDataResource) {
        this.impiankuDataResource = impiankuDataResource
    }

    override fun scrapper(link: String, scrapperCallback: ImpiankuDataResource.ScrapperCallback) {
        impiankuDataResource.scrapper(link, scrapperCallback)
    }
}