package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.kategori.dana.KategoriDanaDataRemote
import com.faishal.saku.repository.kategori.dana.KategoriDanaRepository

class KategoriDanaRepositoryInject {
    companion object {
        fun provideTo(context: Context): KategoriDanaRepository {
            return KategoriDanaRepository(KategoriDanaDataRemote(context))
        }
    }
}