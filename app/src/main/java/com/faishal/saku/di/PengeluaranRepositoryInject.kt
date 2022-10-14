package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.pengeluaran.PengeluaranDataRemote
import com.faishal.saku.repository.pengeluaran.PengeluaranRepository

class PengeluaranRepositoryInject {
    companion object {
        fun provideTo(context: Context): PengeluaranRepository {
            return PengeluaranRepository(PengeluaranDataRemote(context))
        }
    }
}