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
}