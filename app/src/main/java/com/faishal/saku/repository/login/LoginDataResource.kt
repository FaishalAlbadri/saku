package com.faishal.saku.repository.login

import androidx.annotation.NonNull
import com.faishal.saku.data.user.UserItem

interface LoginDataResource {

    fun login(user_email: String, user_password: String, @NonNull loginCallback: LoginCallback)

    interface LoginCallback {

        fun onSuccessLogin(idUser: String, msg: String)
        fun onErrorLogin(msg: String)

    }
}