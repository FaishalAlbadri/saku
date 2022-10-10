package com.faishal.saku.ui.berita

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity

class BeritaDetailActivity : BaseActivity() {

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView

    @BindView(R.id.img_news)
    lateinit var imgNews: ImageView

    @BindView(R.id.txt_date)
    lateinit var txtDate: TextView

    @BindView(R.id.txt_judul)
    lateinit var txtJudul: TextView

    @BindView(R.id.txt_desc)
    lateinit var txtDesc: TextView

    private lateinit var id: String
    private lateinit var judul: String
    private lateinit var desc: String
    private lateinit var img: String
    private lateinit var date: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita_detail)
        ButterKnife.bind(this)

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
            .into(imgNews)

        txtJudul.setText(judul)
        txtDesc.setText(desc)
        txtDate.setText(date)
    }


    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }
}