package com.faishal.saku.repository.login

class LoginRepository : LoginDataResource {

    private var loginDataResource: LoginDataResource

    constructor(loginDataResource: LoginDataResource) {
        this.loginDataResource = loginDataResource
    }

    override fun login(
        user_email: String,
        user_password: String,
        loginCallback: LoginDataResource.LoginCallback
    ) {
        loginDataResource.login(user_email, user_password, loginCallback)
    }
}