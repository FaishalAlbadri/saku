package com.faishal.saku.repository.pengeluaran

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.data.pengeluaran.PengeluaranHariResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengeluaranDataRemote : PengeluaranDataResource {

    private var apiInterface: APIInterface
    private lateinit var pengeluaranResponseCall: Call<PengeluaranHariResponse>
    private lateinit var baseResponseCall: Call<BaseResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)

    }

    override fun pengeluaran(
        id_user: String,
        id_catatan: String,
        pengeluaranCallback: PengeluaranDataResource.PengeluaranCallback
    ) {
        pengeluaranResponseCall = apiInterface.pengeluaran(id_user, id_catatan)
        pengeluaranResponseCall.enqueue(object : Callback<PengeluaranHariResponse> {
            override fun onResponse(
                call: Call<PengeluaranHariResponse>,
                response: Response<PengeluaranHariResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val pengeluaranResponse: PengeluaranHariResponse = response.body()!!
                        val pengeluaranItem: List<PengeluaranHariItem> =
                            pengeluaranResponse.pengeluaranHari
                        pengeluaranCallback.onSuccessPengeluaran(
                            pengeluaranItem,
                            response.body()!!.msg
                        )
                    } else {
                        pengeluaranCallback.onErrorPengeluaran(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<PengeluaranHariResponse>, t: Throwable) {
                pengeluaranCallback.onErrorPengeluaran(Server.CHECK_INTERNET_CONNECTION)
            }

        })
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
        baseResponseCall = apiInterface.pengeluaranAdd(
            id_user,
            id_catatan,
            id_kategori_dana,
            id_kategori_pokok,
            catatan_item_desc,
            catatan_item_harga
        )
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        addPengeluaranCallback.onSuccessAddPengeluaran(response.body()!!.msg)
                    } else {
                        addPengeluaranCallback.onErrorAddPengeluaran(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                addPengeluaranCallback.onErrorAddPengeluaran(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun editPengeluaran(
        id_catatan_item: String,
        catatan_item_desc: String,
        catatan_item_harga: String,
        editPengeluaranCallback: PengeluaranDataResource.EditPengeluaranCallback
    ) {
        baseResponseCall =
            apiInterface.pengeluaranEdit(id_catatan_item, catatan_item_desc, catatan_item_harga)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        editPengeluaranCallback.onSuccessEditPengeluaran(response.body()!!.msg)
                    } else {
                        editPengeluaranCallback.onErrorEditPengeluaran(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                editPengeluaranCallback.onErrorEditPengeluaran(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun deletePengeluaran(
        id_catatan_item: String,
        deletePengeluaranCallback: PengeluaranDataResource.DeletePengeluaranCallback
    ) {
        baseResponseCall = apiInterface.pengeluaranDelete(id_catatan_item)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        deletePengeluaranCallback.onSuccessDeletePengeluaran(response.body()!!.msg)
                    } else {
                        deletePengeluaranCallback.onErrorDeletePengeluaran(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                deletePengeluaranCallback.onErrorDeletePengeluaran(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}