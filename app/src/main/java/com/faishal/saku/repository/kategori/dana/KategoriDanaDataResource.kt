package com.faishal.saku.repository.kategori.dana

import androidx.annotation.NonNull
import com.faishal.saku.data.kategori.dana.KategoriDanaItem

interface KategoriDanaDataResource {

    fun kategoriDana(
        id_user: String,
        id_catatan: String,
        @NonNull kategoriDanaCallback: KategoriDanaCallback
    )

    interface KategoriDanaCallback {

        fun onSuccessKategoriDana(danaListItem: List<KategoriDanaItem>, msg: String)

        fun onErrorKategoriDana(msg: String)
    }
}