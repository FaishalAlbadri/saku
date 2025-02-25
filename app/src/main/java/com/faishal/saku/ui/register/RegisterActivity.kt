package com.faishal.saku.ui.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.faishal.saku.R
import com.faishal.saku.base.BaseFullScreenActivity
import com.faishal.saku.databinding.ActivityRegisterBinding
import com.faishal.saku.di.RegisterRepositoryInject
import com.faishal.saku.presenter.register.RegisterContract
import com.faishal.saku.presenter.register.RegisterPresenter
import com.faishal.saku.ui.login.LoginActivity
import com.faishal.saku.util.Util
import com.rengwuxian.materialedittext.MaterialEditText

class RegisterActivity : BaseFullScreenActivity(), RegisterContract.registerView {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var pd: ProgressDialog

    private var _binding: ActivityRegisterBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        binding.btnLogin.setText(Util.fromHtml(getString(R.string.register_info_login)))

        registerPresenter = RegisterPresenter(RegisterRepositoryInject.provideTo(this))
        registerPresenter.onAttachView(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Sedang Register")

        binding.apply {
            btnRegister.setOnClickListener {
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
                        Toast.makeText(this@RegisterActivity, "Password tidak cocok", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            }

            btnLogin.setOnClickListener {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        }
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
