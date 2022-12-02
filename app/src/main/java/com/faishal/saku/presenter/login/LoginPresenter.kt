package com.faishal.saku.presenter.login

import com.faishal.saku.data.user.UserItem
import com.faishal.saku.repository.login.LoginDataResource
import com.faishal.saku.repository.login.LoginRepository

class LoginPresenter : LoginContract.loginPresenter {

    private var loginRepository: LoginRepository
    private lateinit var loginView: LoginContract.loginView

    constructor(loginRepository: LoginRepository) {
        this.loginRepository = loginRepository
    }


    override fun login(user_email: String, user_password: String) {
        loginRepository.login(user_email, user_password, object : LoginDataResource.LoginCallback {
            override fun onSuccessLogin(idUser: String, msg: String) {
                loginView.onSuccessLogin(idUser, msg)
            }

            override fun onErrorLogin(msg: String) {
                loginView.onErrorLogin(msg)
            }

        })
    }

    override fun profile(idUser: String) {
        loginRepository.profile(idUser, object : LoginDataResource.ProfileCallback {
            override fun onSuccessProfile(userItemList: List<UserItem>, msg: String) {
                loginView.onSuccessProfile(userItemList, msg)
            }

            override fun onErrorProfile(msg: String) {
                loginView.onErrorProfile(msg)
            }

        })
    }

    override fun onAttachView(view: LoginContract.loginView) {
        loginView = view
    }

    override fun onDettachView() {

    }
}