package com.faishal.saku.repository.catatan

import androidx.annotation.NonNull
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem

interface CatatanDataResource {

    fun catatan(id_user: String, @NonNull catatanCallback: CatatanCallback)

    interface CatatanCallback {

        fun onSuccessCatatan(
            catatanListItem: List<CatatanItem>,
            userListItem: List<UserItem>,
            dateS: String,
            msg: String
        )

        fun onErrorCatatan(msg: String)

    }
}