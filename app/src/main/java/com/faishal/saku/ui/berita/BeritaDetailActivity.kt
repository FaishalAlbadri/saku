package com.faishal.saku.ui.berita

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.databinding.ActivityBeritaDetailBinding

class BeritaDetailActivity : BaseActivity() {


    private lateinit var id: String
    private lateinit var judul: String
    private lateinit var desc: String
    private lateinit var img: String
    private lateinit var date: String

    private var _binding: ActivityBeritaDetailBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBeritaDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getdata()
    }

    private fun getdata() {
        id = intent.getStringExtra("id").toString()
        judul = intent.getStringExtra("judul").toString()
        desc = intent.getStringExtra("desc").toString()
        img = intent.getStringExtra("img").toString()
        date = intent.getStringExtra("date").toString()

        Glide.with(this)
            .load(Server.BASE_URL_IMG_NEWS + img)
            .apply(RequestOptions().centerCrop())
            .into(binding.imgNews)

        binding.apply {
            txtJudul.setText(judul)
            txtDesc.setText(desc)
            txtDate.setText(date)

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }
}