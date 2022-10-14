package com.faishal.saku.presenter.kategori.dana

import com.faishal.saku.data.kategori.dana.KategoriDanaItem
import com.faishal.saku.repository.kategori.dana.KategoriDanaDataResource
import com.faishal.saku.repository.kategori.dana.KategoriDanaRepository

class KategoriDanaPresenter : KategoriDanaContract.kategoriDanaPresenter {

    private var kategoriDanaRepository: KategoriDanaRepository
    private lateinit var kategoriDanaView: KategoriDanaContract.kategoriDanaView

    constructor(kategoriDanaRepository: KategoriDanaRepository) {
        this.kategoriDanaRepository = kategoriDanaRepository
    }

    override fun kategoriDana(id_user: String, id_catatan: String) {
        kategoriDanaRepository.kategoriDana(
            id_user,
            id_catatan,
            object : KategoriDanaDataResource.KategoriDanaCallback {
                override fun onSuccessKategoriDana(
                    danaListItem: List<KategoriDanaItem>,
                    msg: String
                ) {
                    kategoriDanaView.onSuccessKategoriDana(danaListItem, msg)
                }

                override fun onErrorKategoriDana(msg: String) {
                    kategoriDanaView.onErrorKategoriDana(msg)
                }

            })
    }

    override fun onAttachView(view: KategoriDanaContract.kategoriDanaView) {
        kategoriDanaView = view
    }

    override fun onDettachView() {
    }
}