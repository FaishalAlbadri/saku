package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.register.RegisterDataRemote
import com.faishal.saku.repository.register.RegisterRepository

class RegisterRepositoryInject {
    companion object {
        fun provideTo(context: Context): RegisterRepository {
            return RegisterRepository(RegisterDataRemote(context))
        }
    }
}