package com.faishal.saku.repository.quest

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.BaseResponse
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.impianku.ImpiankuResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestImpiankuDataRemote : QuestImpiankuDataResource {

    private var apiInterface: APIInterface
    private lateinit var impiankuResponseCall: Call<ImpiankuResponse>
    private lateinit var baseResponseCall: Call<BaseResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }

    override fun questImpianku(
        idUser: String,
        questImpiankuGetCallback: QuestImpiankuDataResource.QuestImpiankuGetCallback
    ) {
        impiankuResponseCall = apiInterface.questImpianku(idUser)
        impiankuResponseCall.enqueue(object : Callback<ImpiankuResponse> {
            override fun onResponse(
                call: Call<ImpiankuResponse>,
                response: Response<ImpiankuResponse>
            ) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val impiankuResponse: ImpiankuResponse = response.body()!!
                        val impiankuProgressItem: List<ImpiankuProgressItem> =
                            impiankuResponse.impiankuProgress
                        questImpiankuGetCallback.onSuccessGetQuestImpianku(
                            impiankuProgressItem,
                            response.body()!!.msg
                        )
                    } else {
                        questImpiankuGetCallback.onErrorGetQuestImpianku("Tidak ada data impianku")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ImpiankuResponse>, t: Throwable) {
                questImpiankuGetCallback.onErrorGetQuestImpianku(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun updateQuestImpianku(
        idImpianku: String,
        questImpiankuUpdateCallback: QuestImpiankuDataResource.QuestImpiankuUpdateCallback
    ) {
        baseResponseCall = apiInterface.updateQuestImpianku(idImpianku)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        questImpiankuUpdateCallback.onSuccessUpdateQuestImpianku(response.body()!!.msg)
                    } else {
                        questImpiankuUpdateCallback.onErrorUpdateQuestImpianku(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                questImpiankuUpdateCallback.onErrorUpdateQuestImpianku(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}