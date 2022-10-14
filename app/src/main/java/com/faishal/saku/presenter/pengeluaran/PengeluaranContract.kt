package com.faishal.saku.presenter.pengeluaran

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem

class PengeluaranContract {

    interface pengeluaranView {
        fun onSuccessPengeluaran(pengeluaranListItem: List<PengeluaranHariItem>, msg: String)
        fun onErrorPengeluaran(msg: String)
    }

    interface pengeluaranPresenter : BasePresenter<pengeluaranView> {
        fun pengeluaran(id_user: String, id_catatan: String)
    }

}