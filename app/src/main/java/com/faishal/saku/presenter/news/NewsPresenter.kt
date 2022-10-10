package com.faishal.saku.presenter.news

import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.repository.news.NewsDataResource
import com.faishal.saku.repository.news.NewsRepository

class NewsPresenter : NewsContract.newsPresenter {

    private var newsRepository: NewsRepository
    private lateinit var newsView: NewsContract.newsView

    constructor(newsRepository: NewsRepository) {
        this.newsRepository = newsRepository
    }

    override fun news(status: String) {
        newsRepository.news(status, object: NewsDataResource.NewsCallback {
            override fun onSuccessNews(newsListItem: List<NewsItem>, msg: String) {
                newsView.onSuccessNews(newsListItem, msg)
            }

            override fun onErrorNews(msg: String) {
                newsView.onErrorNews(msg)
            }

        })
    }

    override fun onAttachView(view: NewsContract.newsView) {
        newsView = view
    }

    override fun onDettachView() {
    }
}