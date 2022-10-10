package com.faishal.saku.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.faishal.saku.R
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.ui.home.HomeActivity
import com.faishal.saku.ui.login.LoginActivity
import com.faishal.saku.util.SessionManager

class SplashScreenActivity : BaseFullScreenActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getSession()

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.getIdUser() == null) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(applicationContext, HomeActivity::class.java))
                finish()
            }
        }, 3000)
    }

    private fun getSession() {
        sessionManager = SessionManager(this)
    }
}