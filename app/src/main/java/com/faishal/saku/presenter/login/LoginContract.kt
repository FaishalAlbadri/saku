package com.faishal.saku.presenter.login

import com.faishal.saku.base.BasePresenter

class LoginContract {

    interface loginView {
        fun onSuccessLogin(idUser: String, msg: String)
        fun onErrorLogin(msg: String)
    }

    interface loginPresenter : BasePresenter<loginView> {
        fun login(user_email: String, user_password: String)
    }
}