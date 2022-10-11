package com.faishal.saku.repository.catatan

import androidx.annotation.NonNull
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem

interface CatatanDataResource {

    fun catatan(id_user: String, @NonNull catatanCallback: CatatanCallback)

    fun catatanAdd(id_user: String, pendapatan: String, waktu: String, @NonNull addCatatanCallback: AddCatatanCallback)

    interface CatatanCallback {

        fun onSuccessCatatan(
            catatanListItem: List<CatatanItem>,
            userListItem: List<UserItem>,
            dateS: String,
            msg: String
        )

        fun onErrorCatatan(msg: String)

    }

    interface AddCatatanCallback {
        fun onSuccessAddCatatan(msg: String)
        fun onFailedAddCatatan(msg: String)
    }
}