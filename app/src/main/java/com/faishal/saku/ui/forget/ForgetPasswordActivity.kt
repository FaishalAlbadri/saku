package com.faishal.saku.ui.forget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.rengwuxian.materialedittext.MaterialEditText

class ForgetPasswordActivity : BaseActivity() {

    @BindView(R.id.btn_forget_password)
    lateinit var btnForgetPassword: Button

    @BindView(R.id.edt_email)
    lateinit var edtEmail: MaterialEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_forget_password)
    fun onBtnForgetPasswordClicked() {
        if (edtEmail.text.toString().isEmpty()) {
            edtEmail.setError("Email masih kosong!")
            edtEmail.requestFocus()
        } else {
            Toast.makeText(
                this,
                "Silahkan ubah passwordmu, link terkirim di email " + edtEmail.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}