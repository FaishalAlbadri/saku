package com.faishal.saku.presenter.news

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.news.NewsItem

class NewsContract {

    interface newsView {
        fun onSuccessNews(newsListItem: List<NewsItem>, msg: String)
        fun onErrorNews(msg: String)
    }

    interface newsPresenter: BasePresenter<newsView> {
        fun news(status: String)
    }
}