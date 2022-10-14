package com.faishal.saku.repository.pengeluaran

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.data.pengeluaran.PengeluaranHariResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PengeluaranDataRemote : PengeluaranDataResource {

    private var apiInterface: APIInterface
    private lateinit var pengeluaranResponseCall: Call<PengeluaranHariResponse>

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
}