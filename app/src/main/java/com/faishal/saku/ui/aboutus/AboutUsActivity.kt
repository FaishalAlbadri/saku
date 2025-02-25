package com.faishal.saku.ui.aboutus

import android.os.Bundle
import com.bumptech.glide.Glide
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.databinding.ActivityAboutUsBinding

class AboutUsActivity : BaseActivity() {

    private var _binding: ActivityAboutUsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            Glide.with(this@AboutUsActivity)
                .load(getImage("faishal"))
                .circleCrop()
                .into(imgFaishal)

            Glide.with(this@AboutUsActivity)
                .load(getImage("rizan"))
                .circleCrop()
                .into(imgRizan)
            Glide.with(this@AboutUsActivity)
                .load(getImage("raffry"))
                .circleCrop()
                .into(imgRaffry)
            Glide.with(this@AboutUsActivity)
                .load(getImage("ijal"))
                .circleCrop()
                .into(imgIjal)
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    fun getImage(imgName: String): Int {
        var drawableResourceId: Int =
            this.resources.getIdentifier(imgName, "drawable", this.packageName)
        return drawableResourceId
    }
}