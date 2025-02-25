package com.faishal.saku.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import com.faishal.saku.R
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.data.user.UserItem
import com.faishal.saku.databinding.ActivityLoginBinding
import com.faishal.saku.di.LoginRepositoryInject
import com.faishal.saku.presenter.login.LoginContract
import com.faishal.saku.presenter.login.LoginPresenter
import com.faishal.saku.ui.forget.ForgetPasswordActivity
import com.faishal.saku.ui.home.HomeActivity
import com.faishal.saku.ui.register.RegisterActivity
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.rengwuxian.materialedittext.MaterialEditText


class LoginActivity : BaseFullScreenActivity(), LoginContract.loginView {

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var pd: ProgressDialog
    private lateinit var sessionManager: SessionManager

    private var _binding: ActivityLoginBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }

    private fun setView() {
        binding.apply {
            btnRegister.setText(Util.fromHtml(getString(R.string.login_info_register)))

            btnShowPass.setOnCheckedChangeListener { compoundButton: CompoundButton?, isChecked: Boolean ->
                if (isChecked) {
                    edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                } else {
                    edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                }
            }

            btnLogin.setOnClickListener {
                var status = true
                var email: String = edtEmail.text.toString()
                var password: String = edtPassword.text.toString()

                if (email.isEmpty()) {
                    status = false
                    edtEmail.setError("Email tidak boleh kosong")
                    edtEmail.requestFocus()
                }

                if (password.isEmpty()) {
                    status = false
                    edtPassword.setError("Password tidak boleh kosong")
                    edtPassword.requestFocus()
                }

                if (status) {
                    pd.show()
                    loginPresenter.login(email, password)
                } else {
                    Toast.makeText(this@LoginActivity, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(applicationContext, RegisterActivity::class.java))
                finish()
            }
            btnForgetPassword.setOnClickListener {
                startActivity(Intent(applicationContext, ForgetPasswordActivity::class.java))
            }
        }

        loginPresenter = LoginPresenter(LoginRepositoryInject.provideTo(this))
        loginPresenter.onAttachView(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Sedang Login")

        sessionManager = SessionManager(this)
    }

    override fun onSuccessLogin(idUser: String, msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        sessionManager.createUser(idUser)
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }

    override fun onErrorLogin(msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessProfile(userItemList: List<UserItem>, msg: String) {

    }

    override fun onErrorProfile(msg: String) {

    }
}