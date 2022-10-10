package com.faishal.saku.repository.news

import androidx.annotation.NonNull
import com.faishal.saku.data.news.NewsItem

interface NewsDataResource {

    fun news(status: String, @NonNull newsCallback: NewsCallback)

    interface NewsCallback {
        fun onSuccessNews(newsListItem: List<NewsItem>, msg: String)
        fun onErrorNews(msg: String)
    }
}