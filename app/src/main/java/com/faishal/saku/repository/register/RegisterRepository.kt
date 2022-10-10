package com.faishal.saku.repository.register

class RegisterRepository : RegisterDataResource {

    private var registerDataResource: RegisterDataResource

    constructor(registerDataResource: RegisterDataResource) {
        this.registerDataResource = registerDataResource
    }

    override fun register(
        user_name: String,
        user_email: String,
        user_phone: String,
        user_password: String,
        registerCallback: RegisterDataResource.RegisterCallback
    ) {
        registerDataResource.register(
            user_name,
            user_email,
            user_phone,
            user_password,
            registerCallback
        )
    }
}