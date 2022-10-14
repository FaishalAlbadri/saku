package com.faishal.saku.repository.kategori.dana

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.kategori.dana.KategoriDanaItem
import com.faishal.saku.data.kategori.dana.KategoriDanaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KategoriDanaDataRemote : KategoriDanaDataResource {

    private var apiInterface: APIInterface
    private lateinit var kategoriDanaResponseCall: Call<KategoriDanaResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)

    }


    override fun kategoriDana(
        id_user: String,
        id_catatan: String,
        kategoriDanaCallback: KategoriDanaDataResource.KategoriDanaCallback
    ) {
        kategoriDanaResponseCall = apiInterface.kategoriDana(id_user, id_catatan)
        kategoriDanaResponseCall.enqueue(object : Callback<KategoriDanaResponse> {
            override fun onResponse(
                call: Call<KategoriDanaResponse>,
                response: Response<KategoriDanaResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val kategoriDanaResponse: KategoriDanaResponse = response.body()!!
                        val kategoriDanaItem: List<KategoriDanaItem> =
                            kategoriDanaResponse.kategoriDana
                        kategoriDanaCallback.onSuccessKategoriDana(
                            kategoriDanaItem,
                            response.body()!!.msg
                        )
                    } else {
                        kategoriDanaCallback.onErrorKategoriDana(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<KategoriDanaResponse>, t: Throwable) {
                kategoriDanaCallback.onErrorKategoriDana(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}