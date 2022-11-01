package com.faishal.saku.presenter.catatan

import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem
import com.faishal.saku.repository.catatan.CatatanDataResource
import com.faishal.saku.repository.catatan.CatatanRepository

class CatatanPresenter : CatatanContract.catatanPresenter {

    private var catatanRepository: CatatanRepository
    private lateinit var catatanView: CatatanContract.catatanView

    constructor(catatanRepository: CatatanRepository) {
        this.catatanRepository = catatanRepository
    }

    override fun catatan(id_user: String) {
        catatanRepository.catatan(id_user, object : CatatanDataResource.CatatanCallback {
            override fun onSuccessCatatan(
                catatanListItem: List<CatatanItem>,
                userListItem: List<UserItem>,
                dateS: String,
                msg: String
            ) {
                catatanView.onSuccessCatatan(catatanListItem, userListItem, dateS, msg)
            }

            override fun onBlankCatatan(userListItem: List<UserItem>, dateS: String, msg: String) {
                catatanView.onBlankCatatan(userListItem, dateS, msg)
            }

            override fun onErrorCatatan(msg: String) {
                catatanView.onErrorCatatan(msg)
            }

        })
    }

    override fun catatanAdd(id_user: String, pendapatan: String, waktu: String) {
        catatanRepository.catatanAdd(
            id_user,
            pendapatan,
            waktu,
            object : CatatanDataResource.AddCatatanCallback {
                override fun onSuccessAddCatatan(msg: String) {
                    catatanView.onSuccessAddCatatan(msg)
                }

                override fun onFailedAddCatatan(msg: String) {
                    catatanView.onFailedAddCatatan(msg)
                }

            })
    }

    override fun onAttachView(view: CatatanContract.catatanView) {
        catatanView = view
    }

    override fun onDettachView() {
        TODO("Not yet implemented")
    }
}