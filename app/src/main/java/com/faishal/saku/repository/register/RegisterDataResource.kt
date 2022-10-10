package com.faishal.saku.repository.register

import androidx.annotation.NonNull

interface RegisterDataResource {

    fun register(
        user_name: String,
        user_email: String,
        user_phone: String,
        user_password: String,
        @NonNull registerCallback: RegisterCallback
    )

    interface RegisterCallback {
        fun onSuccessRegister(msg: String)
        fun onErrorRegister(msg: String)
    }
}