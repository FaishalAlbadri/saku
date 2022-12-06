package com.faishal.saku.repository.impianku

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.impianku.ImpiankuResponse
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.data.scrapper.ScrapperResponse
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ImpiankuDataRemote : ImpiankuDataResource {

    private var apiInterface: APIInterface
    private lateinit var scrapperResponseCall: Call<ScrapperResponse>
    private lateinit var impiankuResponseCall: Call<ImpiankuResponse>
    private lateinit var baseResponseCall: Call<BaseResponse>

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

    override fun impianku(
        idUser: String,
        impiankuGetCallback: ImpiankuDataResource.ImpiankuGetCallback
    ) {
        impiankuResponseCall = apiInterface.impianku(idUser)
        impiankuResponseCall.enqueue(object : Callback<ImpiankuResponse> {
            override fun onResponse(
                call: Call<ImpiankuResponse>,
                response: Response<ImpiankuResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val impiankuResponse: ImpiankuResponse = response.body()!!
                        val impiankuProgressItem: List<ImpiankuProgressItem> = impiankuResponse.impiankuProgress
                        val impiankuFinishedItem: List<ImpiankuFinishedItem> = impiankuResponse.impiankuFinished
                        impiankuGetCallback.onSuccessGetImpianku(impiankuProgressItem, impiankuFinishedItem, response.body()!!.msg)
                    } else {
                        impiankuGetCallback.onErrorGetImpianku("Tidak ada data impianku")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ImpiankuResponse>, t: Throwable) {
                impiankuGetCallback.onErrorGetImpianku(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun addImpiankuShopee(
        idUser: String,
        title: String,
        price: String,
        img: String,
        days: String,
        link: String,
        addImpiankuShopeeCallback: ImpiankuDataResource.AddImpiankuShopeeCallback
    ) {
        baseResponseCall = apiInterface.addImpiankuShopee(idUser, title, price, img, days, link)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        addImpiankuShopeeCallback.onSuccessAddImpiankuShopee(response.body()!!.msg)
                    } else {
                        addImpiankuShopeeCallback.onErrorAddImpiankuShopee(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                addImpiankuShopeeCallback.onErrorAddImpiankuShopee(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun addImpiankuManual(
        idUser: String,
        title: String,
        price: String,
        img: File,
        days: String,
        addImpiankuManualCallback: ImpiankuDataResource.AddImpiankuManualCallback
    ) {
        val map: MutableMap<String, RequestBody> = HashMap()
        try {
            val fileBody = RequestBody.create(MediaType.parse("image/*"), img)
            map.put("impianku_img\"; filename=\"" + img.getName() + "\"", fileBody)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        map.put("id_user", RequestBody.create(MediaType.parse("text/plain"), idUser))
        map.put("impianku_title", RequestBody.create(MediaType.parse("text/plain"), title))
        map.put("impianku_price", RequestBody.create(MediaType.parse("text/plain"), price))
        map.put("impianku_days", RequestBody.create(MediaType.parse("text/plain"), days))

        baseResponseCall = apiInterface.addImpiankuManual(map)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        addImpiankuManualCallback.onSuccessAddImpiankuManual(response.body()!!.msg)
                    } else {
                        addImpiankuManualCallback.onErrorAddImpiankuManual(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                addImpiankuManualCallback.onErrorAddImpiankuManual(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}