package com.faishal.saku.repository.catatan

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.CatatanResponse
import com.faishal.saku.data.catatan.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatatanDataRemote : CatatanDataResource {

    private var apiInterface: APIInterface
    private lateinit var catatanResponseCall: Call<CatatanResponse>
    private lateinit var addCatatanResponseCall: Call<CatatanResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }

    override fun catatan(id_user: String, catatanCallback: CatatanDataResource.CatatanCallback) {
        catatanResponseCall = apiInterface.catatan(id_user)
        catatanResponseCall.enqueue(object : Callback<CatatanResponse> {
            override fun onResponse(
                call: Call<CatatanResponse>,
                response: Response<CatatanResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val catatanResponse: CatatanResponse = response.body()!!
                        val catatanItem: List<CatatanItem> = catatanResponse.catatan
                        val userItem: List<UserItem> = catatanResponse.user
                        val date: String = catatanResponse.date!!
                        catatanCallback.onSuccessCatatan(
                            catatanItem,
                            userItem,
                            date,
                            response.body()!!.msg
                        )
                    } else {
                        catatanCallback.onErrorCatatan(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<CatatanResponse>, t: Throwable) {
                catatanCallback.onErrorCatatan(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun catatanAdd(
        id_user: String,
        pendapatan: String,
        waktu: String,
        addCatatanCallback: CatatanDataResource.AddCatatanCallback
    ) {
        addCatatanResponseCall = apiInterface.catatanAdd(id_user, pendapatan, waktu)
        addCatatanResponseCall.enqueue(object : Callback<CatatanResponse> {
            override fun onResponse(
                call: Call<CatatanResponse>,
                response: Response<CatatanResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        addCatatanCallback.onSuccessAddCatatan("Berhasil")
                    } else {
                        addCatatanCallback.onFailedAddCatatan("Gagal")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<CatatanResponse>, t: Throwable) {
                addCatatanCallback.onFailedAddCatatan(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}