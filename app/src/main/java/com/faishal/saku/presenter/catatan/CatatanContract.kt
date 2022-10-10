package com.faishal.saku.presenter.catatan

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem

class CatatanContract {

    interface catatanView {
        fun onSuccessCatatan(
            catatanListItem: List<CatatanItem>,
            userListItem: List<UserItem>,
            dateS: String,
            msg: String
        )

        fun onErrorCatatan(msg: String)
    }

    interface catatanPresenter : BasePresenter<catatanView> {
        fun catatan(id_user: String)
    }
}