package com.faishal.saku.repository.register

import android.content.Context
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterDataRemote : RegisterDataResource {

    private var apiInterface: APIInterface
    private lateinit var baseResponseCall: Call<BaseResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }

    override fun register(
        user_name: String,
        user_email: String,
        user_phone: String,
        user_password: String,
        registerCallback: RegisterDataResource.RegisterCallback
    ) {
        baseResponseCall = apiInterface.register(user_name, user_email, user_phone, user_password)
        baseResponseCall.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        registerCallback.onSuccessRegister(response.body()!!.msg)
                    } else {
                        registerCallback.onErrorRegister(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                registerCallback.onErrorRegister(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}