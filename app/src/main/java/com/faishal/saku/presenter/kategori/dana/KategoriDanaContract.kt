package com.faishal.saku.presenter.kategori.dana

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.kategori.dana.KategoriDanaItem

class KategoriDanaContract {

    interface kategoriDanaView {
        fun onSuccessKategoriDana(danaListItem: List<KategoriDanaItem>, msg: String)
        fun onErrorKategoriDana(msg: String)
    }

    interface kategoriDanaPresenter : BasePresenter<kategoriDanaView> {
        fun kategoriDana(id_user: String, id_catatan: String)
    }

}