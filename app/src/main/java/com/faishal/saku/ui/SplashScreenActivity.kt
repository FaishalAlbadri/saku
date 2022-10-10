package com.faishal.saku.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.di.LoginRepositoryInject
import com.faishal.saku.presenter.login.LoginContract
import com.faishal.saku.presenter.login.LoginPresenter
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