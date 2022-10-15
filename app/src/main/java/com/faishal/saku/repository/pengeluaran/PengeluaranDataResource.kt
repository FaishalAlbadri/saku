package com.faishal.saku.repository.pengeluaran

import androidx.annotation.NonNull
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem

interface PengeluaranDataResource {

    fun pengeluaran(
        id_user: String,
        id_catatan: String,
        @NonNull PengeluaranCallback: PengeluaranCallback
    )

    fun addPengeluaran(
        id_user: String,
        id_catatan: String,
        id_kategori_dana: String,
        id_kategori_pokok: String,
        catatan_item_desc: String,
        catatan_item_harga: String,
        @NonNull addPengeluaranCallback: AddPengeluaranCallback
    )

    fun editPengeluaran(
        id_catatan_item: String,
        catatan_item_desc: String,
        catatan_item_harga: String,
        @NonNull editPengeluaranCallback: EditPengeluaranCallback
    )

    fun deletePengeluaran(
        id_catatan_item: String,
        @NonNull deletePengeluaranCallback: DeletePengeluaranCallback
    )

    interface PengeluaranCallback {
        fun onSuccessPengeluaran(pengeluaranListItem: List<PengeluaranHariItem>, msg: String)
        fun onErrorPengeluaran(msg: String)
    }

    interface AddPengeluaranCallback {
        fun onSuccessAddPengeluaran(msg: String)
        fun onErrorAddPengeluaran(msg: String)
    }

    interface EditPengeluaranCallback {
        fun onSuccessEditPengeluaran(msg: String)
        fun onErrorEditPengeluaran(msg: String)
    }

    interface DeletePengeluaranCallback {
        fun onSuccessDeletePengeluaran(msg: String)
        fun onErrorDeletePengeluaran(msg: String)
    }

}