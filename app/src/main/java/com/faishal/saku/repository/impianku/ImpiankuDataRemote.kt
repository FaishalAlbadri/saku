package com.faishal.saku.repository.impianku

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.data.scrapper.ScrapperResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImpiankuDataRemote : ImpiankuDataResource {

    private var apiInterface: APIInterface
    private lateinit var scrapperResponseCall: Call<ScrapperResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }


    override fun scrapper(link: String, scrapperCallback: ImpiankuDataResource.ScrapperCallback) {
        scrapperResponseCall = apiInterface.scrapperShoope(link)
        scrapperResponseCall.enqueue(object : Callback<ScrapperResponse> {
            override fun onResponse(
                call: Call<ScrapperResponse>,
                response: Response<ScrapperResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val scrapperResponse: ScrapperResponse = response.body()!!
                        val scrapperItem: List<ScrapperItem> = scrapperResponse.scrapperItem
                        scrapperCallback.onSuccessScrapper(scrapperItem, response.body()!!.msg)
                    } else {
                        scrapperCallback.onErrorScrapper("Terjadi Kesalahan Pada Server")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ScrapperResponse>, t: Throwable) {
                scrapperCallback.onErrorScrapper(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}