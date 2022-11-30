package com.faishal.saku.ui.aboutus

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity

class AboutUsActivity : BaseActivity() {

    @BindView(R.id.img_faishal)
    lateinit var imgFaishal: ImageView

    @BindView(R.id.img_rizan)
    lateinit var imgRizan: ImageView

    @BindView(R.id.img_raffry)
    lateinit var imgRaffry: ImageView

    @BindView(R.id.img_ijal)
    lateinit var imgIjal: ImageView

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        ButterKnife.bind(this)

        Glide.with(this)
            .load(getImage("faishal"))
            .circleCrop()
            .into(imgFaishal)

        Glide.with(this)
            .load(getImage("rizan"))
            .circleCrop()
            .into(imgRizan)
        Glide.with(this)
            .load(getImage("ic_logo"))
            .circleCrop()
            .into(imgRaffry)
        Glide.with(this)
            .load(getImage("ic_logo"))
            .circleCrop()
            .into(imgIjal)
    }

    fun getImage(imgName: String): Int {
        var drawableResourceId: Int =
            this.resources.getIdentifier(imgName, "drawable", this.packageName)
        return drawableResourceId
    }


    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }
}