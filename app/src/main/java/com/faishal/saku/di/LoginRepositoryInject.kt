package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.login.LoginDataRemote
import com.faishal.saku.repository.login.LoginRepository

class LoginRepositoryInject {
    companion object {
        fun provideTo(context: Context) : LoginRepository {
            return LoginRepository(LoginDataRemote(context))
        }
    }
}