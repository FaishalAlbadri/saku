package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.impianku.ImpiankuDataRemote
import com.faishal.saku.repository.impianku.ImpiankuRepository

class ImpiankuRepositoryInject {
    companion object {
        fun provideTo(context: Context): ImpiankuRepository {
            return ImpiankuRepository(ImpiankuDataRemote(context))
        }
    }
}