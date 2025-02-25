package com.faishal.saku.ui.forget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.databinding.ActivityForgetPasswordBinding
import com.rengwuxian.materialedittext.MaterialEditText

class ForgetPasswordActivity : BaseActivity() {

    private var _binding: ActivityForgetPasswordBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnForgetPassword.setOnClickListener {
                if (edtEmail.text.toString().isEmpty()) {
                    edtEmail.setError("Email masih kosong!")
                    edtEmail.requestFocus()
                } else {
                    Toast.makeText(
                        this@ForgetPasswordActivity,
                        "Silahkan ubah passwordmu, link terkirim di email " + edtEmail.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    onBackPressed()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}