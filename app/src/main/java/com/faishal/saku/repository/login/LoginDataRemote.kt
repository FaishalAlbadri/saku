package com.faishal.saku.repository.login

import android.content.Context
import android.util.Log
import com.faishal.saku.api.APIClient
import com.faishal.saku.api.APIInterface
import com.faishal.saku.api.Server
import com.faishal.saku.data.user.UserItem
import com.faishal.saku.data.user.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginDataRemote : LoginDataResource {

    private var apiInterface: APIInterface
    private lateinit var userResponseCall: Call<UserResponse>

    constructor(context: Context) {
        apiInterface = APIClient.getRetrofit(context)!!.create(APIInterface::class.java)
    }

    override fun login(
        user_email: String,
        user_password: String,
        loginCallback: LoginDataResource.LoginCallback
    ) {

        userResponseCall = apiInterface.login(user_email, user_password)
        userResponseCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val userResponse: UserResponse = response.body()!!
                        val userItem: List<UserItem> = userResponse.user
                        val idUser: String = userItem.get(0).idUser
                        loginCallback.onSuccessLogin(idUser, response.body()!!.msg)
                    } else {
                        loginCallback.onErrorLogin(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                loginCallback.onErrorLogin(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }

    override fun profile(idUser: String, profileCallback: LoginDataResource.ProfileCallback) {
        userResponseCall = apiInterface.profile(idUser)
        userResponseCall.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                try {
                    if (response.body()!!.msg.equals("Berhasil")) {
                        val userResponse: UserResponse = response.body()!!
                        val userItem: List<UserItem> = userResponse.user
                        profileCallback.onSuccessProfile(userItem, response.body()!!.msg)
                    } else {
                        profileCallback.onErrorProfile(response.body()!!.msg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                profileCallback.onErrorProfile(Server.CHECK_INTERNET_CONNECTION)
            }

        })
    }
}