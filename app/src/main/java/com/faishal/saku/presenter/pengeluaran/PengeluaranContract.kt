package com.faishal.saku.presenter.pengeluaran

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem

class PengeluaranContract {

    interface pengeluaranView {
        fun onSuccessPengeluaran(pengeluaranListItem: List<PengeluaranHariItem>, msg: String)
        fun onErrorPengeluaran(msg: String)

        fun onSuccessAddPengeluaran(msg: String)
        fun onErrorAddPengeluaran(msg: String)

        fun onSuccessEditPengeluaran(msg: String)
        fun onErrorEditPengeluaran(msg: String)

        fun onSuccessDeletePengeluaran(msg: String)
        fun onErrorDeletePengeluaran(msg: String)
    }

    interface pengeluaranPresenter : BasePresenter<pengeluaranView> {
        fun pengeluaran(id_user: String, id_catatan: String)

        fun addPengeluaran(
            id_user: String,
            id_catatan: String,
            id_kategori_dana: String,
            id_kategori_pokok: String,
            catatan_item_desc: String,
            catatan_item_harga: String
        )

        fun editPengeluaran(
            id_catatan_item: String,
            catatan_item_desc: String,
            catatan_item_harga: String
        )

        fun deletePengeluaran(id_catatan_item: String)
    }

}