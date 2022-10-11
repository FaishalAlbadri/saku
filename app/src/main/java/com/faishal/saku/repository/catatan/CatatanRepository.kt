package com.faishal.saku.repository.catatan

class CatatanRepository : CatatanDataResource {

    private var catatanDataResource: CatatanDataResource

    constructor(catatanDataResource: CatatanDataResource) {
        this.catatanDataResource = catatanDataResource
    }

    override fun catatan(id_user: String, catatanCallback: CatatanDataResource.CatatanCallback) {
        catatanDataResource.catatan(id_user, catatanCallback)
    }

    override fun catatanAdd(
        id_user: String,
        pendapatan: String,
        waktu: String,
        addCatatanCallback: CatatanDataResource.AddCatatanCallback
    ) {
        catatanDataResource.catatanAdd(id_user, pendapatan, waktu, addCatatanCallback)
    }
}