package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.news.NewsDataRemote
import com.faishal.saku.repository.news.NewsRepository

class NewsRepositoryInject {

    companion object {
        fun provideTo(context: Context) : NewsRepository {
            return NewsRepository(NewsDataRemote(context))
        }
    }

}