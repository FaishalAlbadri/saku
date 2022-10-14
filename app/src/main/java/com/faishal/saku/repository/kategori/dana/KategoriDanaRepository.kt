package com.faishal.saku.repository.kategori.dana

class KategoriDanaRepository: KategoriDanaDataResource {

    private var kategoriDanaDataResource: KategoriDanaDataResource

    constructor(kategoriDanaDataResource: KategoriDanaDataResource) {
     this.kategoriDanaDataResource = kategoriDanaDataResource
    }

    override fun kategoriDana(
        id_user: String,
        id_catatan: String,
        kategoriDanaCallback: KategoriDanaDataResource.KategoriDanaCallback
    ) {
        kategoriDanaDataResource.kategoriDana(id_user, id_catatan, kategoriDanaCallback)
    }
}