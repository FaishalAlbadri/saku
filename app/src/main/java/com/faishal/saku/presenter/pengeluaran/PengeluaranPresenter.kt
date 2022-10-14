package com.faishal.saku.presenter.pengeluaran

import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.repository.pengeluaran.PengeluaranDataResource
import com.faishal.saku.repository.pengeluaran.PengeluaranRepository

class PengeluaranPresenter : PengeluaranContract.pengeluaranPresenter {

    private var pengeluaranRepository: PengeluaranRepository
    private lateinit var pengeluaranView: PengeluaranContract.pengeluaranView

    constructor(pengeluaranRepository: PengeluaranRepository) {
        this.pengeluaranRepository = pengeluaranRepository
    }

    override fun pengeluaran(id_user: String, id_catatan: String) {
        pengeluaranRepository.pengeluaran(
            id_user,
            id_catatan,
            object : PengeluaranDataResource.PengeluaranCallback {
                override fun onSuccessPengeluaran(
                    pengeluaranListItem: List<PengeluaranHariItem>,
                    msg: String
                ) {
                    pengeluaranView.onSuccessPengeluaran(pengeluaranListItem, msg)
                }

                override fun onErrorPengeluaran(msg: String) {
                    pengeluaranView.onErrorPengeluaran(msg)
                }

            })
    }

    override fun onAttachView(view: PengeluaranContract.pengeluaranView) {
        pengeluaranView = view
    }

    override fun onDettachView() {

    }
}