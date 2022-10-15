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

    override fun addPengeluaran(
        id_user: String,
        id_catatan: String,
        id_kategori_dana: String,
        id_kategori_pokok: String,
        catatan_item_desc: String,
        catatan_item_harga: String
    ) {
        pengeluaranRepository.addPengeluaran(id_user,
            id_catatan,
            id_kategori_dana,
            id_kategori_pokok,
            catatan_item_desc,
            catatan_item_harga, object : PengeluaranDataResource.AddPengeluaranCallback {
                override fun onSuccessAddPengeluaran(msg: String) {
                    pengeluaranView.onSuccessAddPengeluaran(msg)
                }

                override fun onErrorAddPengeluaran(msg: String) {
                    pengeluaranView.onErrorAddPengeluaran(msg)
                }

            })
    }

    override fun editPengeluaran(
        id_catatan_item: String,
        catatan_item_desc: String,
        catatan_item_harga: String
    ) {
        pengeluaranRepository.editPengeluaran(
            id_catatan_item,
            catatan_item_desc,
            catatan_item_harga,
            object : PengeluaranDataResource.EditPengeluaranCallback {
                override fun onSuccessEditPengeluaran(msg: String) {
                    pengeluaranView.onSuccessEditPengeluaran(msg)
                }

                override fun onErrorEditPengeluaran(msg: String) {
                    pengeluaranView.onErrorEditPengeluaran(msg)
                }

            })
    }

    override fun deletePengeluaran(id_catatan_item: String) {
        pengeluaranRepository.deletePengeluaran(
            id_catatan_item,
            object : PengeluaranDataResource.DeletePengeluaranCallback {
                override fun onSuccessDeletePengeluaran(msg: String) {
                    pengeluaranView.onSuccessDeletePengeluaran(msg)
                }

                override fun onErrorDeletePengeluaran(msg: String) {
                    pengeluaranView.onErrorDeletePengeluaran(msg)
                }

            })
    }

    override fun onAttachView(view: PengeluaranContract.pengeluaranView) {
        pengeluaranView = view
    }

    override fun onDettachView() {

    }
}