package com.faishal.saku.presenter.login

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.user.UserItem

class LoginContract {

    interface loginView {
        fun onSuccessLogin(idUser: String, msg: String)
        fun onErrorLogin(msg: String)

        fun onSuccessProfile(userItemList: List<UserItem>, msg: String)
        fun onErrorProfile(msg: String)
    }

    interface loginPresenter : BasePresenter<loginView> {
        fun login(user_email: String, user_password: String)
        fun profile(idUser: String)
    }
}