package com.faishal.saku.presenter.register

import com.faishal.saku.base.BasePresenter

class RegisterContract {

    interface registerView {
        fun onSuccessRegister(msg: String)
        fun onErrorRegister(msg: String)
    }

    interface registerPresenter : BasePresenter<registerView> {
        fun register(user_name: String, user_email: String, user_phone: String, user_password: String)
    }
}