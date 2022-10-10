package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.catatan.CatatanDataRemote
import com.faishal.saku.repository.catatan.CatatanRepository

class CatatanRepositoryInject {
    companion object {
        fun provideTo(context: Context): CatatanRepository {
            return CatatanRepository(CatatanDataRemote(context))
        }
    }
}