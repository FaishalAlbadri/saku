package com.faishal.saku.repository.pengeluaran

import androidx.annotation.NonNull
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem

interface PengeluaranDataResource {

    fun pengeluaran(
        id_user: String,
        id_catatan: String,
        @NonNull PengeluaranCallback: PengeluaranCallback
    )

    interface PengeluaranCallback {

        fun onSuccessPengeluaran(pengeluaranListItem: List<PengeluaranHariItem>, msg: String)

        fun onErrorPengeluaran(msg: String)
    }

}