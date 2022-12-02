package com.faishal.saku.repository.login

import androidx.annotation.NonNull
import com.faishal.saku.data.user.UserItem

interface LoginDataResource {

    fun login(user_email: String, user_password: String, @NonNull loginCallback: LoginCallback)

    fun profile(idUser: String, @NonNull profileCallback: ProfileCallback)

    interface LoginCallback {

        fun onSuccessLogin(idUser: String, msg: String)
        fun onErrorLogin(msg: String)

    }

    interface ProfileCallback {
        fun onSuccessProfile(userItemList: List<UserItem>, msg: String)
        fun onErrorProfile(msg: String)
    }
}