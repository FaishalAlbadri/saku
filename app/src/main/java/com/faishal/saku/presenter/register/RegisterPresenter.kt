package com.faishal.saku.presenter.register

import com.faishal.saku.repository.register.RegisterDataResource
import com.faishal.saku.repository.register.RegisterRepository

class RegisterPresenter : RegisterContract.registerPresenter {

    private var registerRepository: RegisterRepository
    private lateinit var registerView: RegisterContract.registerView

    constructor(registerRepository: RegisterRepository) {
        this.registerRepository = registerRepository
    }

    override fun register(
        user_name: String,
        user_email: String,
        user_phone: String,
        user_password: String
    ) {
        registerRepository.register(
            user_name,
            user_email,
            user_phone,
            user_password,
            object : RegisterDataResource.RegisterCallback {
                override fun onSuccessRegister(msg: String) {
                    registerView.onSuccessRegister(msg)
                }

                override fun onErrorRegister(msg: String) {
                    registerView.onErrorRegister(msg)
                }

            })
    }

    override fun onAttachView(view: RegisterContract.registerView) {
        registerView = view
    }

    override fun onDettachView() {

    }
}