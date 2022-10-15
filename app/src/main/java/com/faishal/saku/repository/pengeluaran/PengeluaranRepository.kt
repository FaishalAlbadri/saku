package com.faishal.saku.repository.pengeluaran

class PengeluaranRepository : PengeluaranDataResource {

    private val pengeluaranDataResource: PengeluaranDataResource

    constructor(pengeluaranDataResource: PengeluaranDataResource) {
        this.pengeluaranDataResource = pengeluaranDataResource
    }

    override fun pengeluaran(
        id_user: String,
        id_catatan: String,
        PengeluaranCallback: PengeluaranDataResource.PengeluaranCallback
    ) {
        pengeluaranDataResource.pengeluaran(id_user, id_catatan, PengeluaranCallback)
    }

    override fun addPengeluaran(
        id_user: String,
        id_catatan: String,
        id_kategori_dana: String,
        id_kategori_pokok: String,
        catatan_item_desc: String,
        catatan_item_harga: String,
        addPengeluaranCallback: PengeluaranDataResource.AddPengeluaranCallback
    ) {
        pengeluaranDataResource.addPengeluaran(
            id_user,
            id_catatan,
            id_kategori_dana,
            id_kategori_pokok,
            catatan_item_desc,
            catatan_item_harga,
            addPengeluaranCallback
        )
    }

    override fun editPengeluaran(
        id_catatan_item: String,
        catatan_item_desc: String,
        catatan_item_harga: String,
        editPengeluaranCallback: PengeluaranDataResource.EditPengeluaranCallback
    ) {
        pengeluaranDataResource.editPengeluaran(
            id_catatan_item,
            catatan_item_desc,
            catatan_item_harga,
            editPengeluaranCallback
        )
    }

    override fun deletePengeluaran(
        id_catatan_item: String,
        deletePengeluaranCallback: PengeluaranDataResource.DeletePengeluaranCallback
    ) {
        pengeluaranDataResource.deletePengeluaran(id_catatan_item, deletePengeluaranCallback)
    }
}