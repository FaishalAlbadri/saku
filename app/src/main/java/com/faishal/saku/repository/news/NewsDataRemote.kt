package com.faishal.saku.repository.news

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.CatatanResponse
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.data.news.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsDataRemote : NewsDataResource {

    private var apiInterface: APIInterface
    private lateinit var newsResponseCall: Call<NewsResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }

    override fun news(status: String, newsCallback: NewsDataResource.NewsCallback) {
        newsResponseCall = apiInterface.news(status)
        newsResponseCall.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val newsResponse: NewsResponse = response.body()!!
                        val newsItem: List<NewsItem> = newsResponse.news
                        newsCallback.onSuccessNews(newsItem, response.body()!!.msg)

                    } else {
                        newsCallback.onErrorNews(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                newsCallback.onErrorNews(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}