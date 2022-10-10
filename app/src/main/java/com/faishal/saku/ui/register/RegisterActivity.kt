package com.faishal.saku.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.di.RegisterRepositoryInject
import com.faishal.saku.presenter.register.RegisterContract
import com.faishal.saku.presenter.register.RegisterPresenter
import com.faishal.saku.ui.login.LoginActivity
import com.faishal.saku.util.Util
import com.rengwuxian.materialedittext.MaterialEditText

class RegisterActivity : BaseFullScreenActivity(), RegisterContract.registerView {

    @BindView(R.id.btn_register)
    lateinit var btnRegister: Button

    @BindView(R.id.edt_name)
    lateinit var edtName: MaterialEditText

    @BindView(R.id.edt_phone)
    lateinit var edtPhone: MaterialEditText

    @BindView(R.id.edt_email)
    lateinit var edtEmail: MaterialEditText

    @BindView(R.id.edt_password)
    lateinit var edtPassword: MaterialEditText

    @BindView(R.id.edt_repassword)
    lateinit var edtRepassword: MaterialEditText

    @BindView(R.id.btn_login)
    lateinit var btnLogin: TextView

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ButterKnife.bind(this)

        setView()
    }

    private fun setView() {
        btnLogin.setText(Util.fromHtml(getString(R.string.register_info_login)))

        registerPresenter = RegisterPresenter(RegisterRepositoryInject.provideTo(this))
        registerPresenter.onAttachView(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Sedang Register")
    }

    @OnClick(R.id.btn_register)
    fun onBtnRegisterClicked() {
        var status = true
        var name: String = edtName.text.toString()
        var email: String = edtEmail.text.toString()
        var phone: String = edtPhone.text.toString()
        var password: String = edtPassword.text.toString()
        var repassword: String = edtRepassword.text.toString()

        if (name.isEmpty()) {
            status = false
            edtName.setError("Nama pengguna tidak boleh kosong")
            edtName.requestFocus()
        }

        if (email.isEmpty()) {
            status = false
            edtEmail.setError("Email tidak boleh kosong")
            edtEmail.requestFocus()
        }

        if (phone.isEmpty()) {
            status = false
            edtPhone.setError("Nomor handphone tidak boleh kosong")
            edtPhone.requestFocus()
        }

        if (password.isEmpty()) {
            status = false
            edtPassword.setError("Password tidak boleh kosong")
            edtPassword.requestFocus()
        }

        if (repassword.isEmpty()) {
            status = false
            edtRepassword.setError("Ulangi password tidak boleh kosong")
            edtRepassword.requestFocus()
        }

        if (status) {
            pd.show()
            if (repassword.equals(password)) {
                registerPresenter.register(name, email, phone, password)
            } else {
                pd.cancel()
                Toast.makeText(this, "Password tidak cocok", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        }
    }

    @OnClick(R.id.btn_login)
    fun onBtnLoginClicked() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    override fun onSuccessRegister(msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }

    override fun onErrorRegister(msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
