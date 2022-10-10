package com.faishal.saku.ui.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.di.LoginRepositoryInject
import com.faishal.saku.presenter.login.LoginContract
import com.faishal.saku.presenter.login.LoginPresenter
import com.faishal.saku.ui.home.HomeActivity
import com.faishal.saku.ui.register.RegisterActivity
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.rengwuxian.materialedittext.MaterialEditText


class LoginActivity : BaseFullScreenActivity(), LoginContract.loginView {

    @BindView(R.id.btn_register)
    lateinit var btnRegister: TextView
    @BindView(R.id.btn_forget_password)
    lateinit var btnForgetPassword: TextView
    @BindView(R.id.edt_email)
    lateinit var edtEmail: MaterialEditText
    @BindView(R.id.edt_password)
    lateinit var edtPassword: MaterialEditText
    @BindView(R.id.btn_show_pass)
    lateinit var btnShowPass: CheckBox
    @BindView(R.id.btn_login)
    lateinit var btnLogin: Button

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var pd: ProgressDialog
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        setView()
    }

    private fun setView() {
        btnRegister.setText(Util.fromHtml(getString(R.string.login_info_register)))

        btnShowPass.setOnCheckedChangeListener { compoundButton: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                edtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                edtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
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

    @OnClick(R.id.btn_login)
    fun onBtnLoginClicked(){
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
            Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
    }

    @OnClick(R.id.btn_register)
    fun onBtnRegisterClicked(){
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
        finish()
    }

    @OnClick(R.id.btn_forget_password)
    fun onBtnForgetPasswordClicked(){
        Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show()
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
}