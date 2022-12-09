package com.faishal.saku.ui.impianku

import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.adapter.ImpiankuDetailHariAdapter
import com.faishal.saku.api.Server
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.impianku.detail.ImpiankuItem
import com.faishal.saku.data.impianku.detail.PengeluaranHariItem
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.databinding.ActivityImpiankuDetailBinding
import com.faishal.saku.di.ImpiankuRepositoryInject
import com.faishal.saku.presenter.impianku.ImpiankuContract
import com.faishal.saku.presenter.impianku.ImpiankuPresenter
import com.faishal.saku.util.Util
import com.google.android.material.appbar.AppBarLayout

class ImpiankuDetailActivity : AppCompatActivity(), ImpiankuContract.impiankuView {

    private lateinit var binding: ActivityImpiankuDetailBinding
    private lateinit var idImpianku: String
    private lateinit var impiankuPresenter: ImpiankuPresenter
    private lateinit var pd: ProgressDialog
    private lateinit var impiankuDetailHariAdapter: ImpiankuDetailHariAdapter
    private var pengeluaranHariItem: ArrayList<PengeluaranHariItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImpiankuDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    binding.btnBack.setVisibility(View.VISIBLE)
                    binding.txtImpianku.setVisibility(View.VISIBLE)
                    binding.toolbarLinear.setVisibility(View.GONE)
                } else if (isShow) {
                    isShow = false;
                    binding.btnBack.setVisibility(View.GONE)
                    binding.txtImpianku.setVisibility(View.GONE)
                    binding.toolbarLinear.setVisibility(View.VISIBLE)
                }
            }
        })
        backButton()
        getDataIntent()
        setView()
    }

    private fun setView() {
        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        impiankuDetailHariAdapter = ImpiankuDetailHariAdapter(this, pengeluaranHariItem)
        binding.layoutContent.rvRiwayatMenabung.setLayoutManager(LinearLayoutManager(this))
        binding.layoutContent.rvRiwayatMenabung.setAdapter(impiankuDetailHariAdapter)

        impiankuPresenter = ImpiankuPresenter(ImpiankuRepositoryInject.provideTo(this))
        impiankuPresenter.onAttachView(this)
        impiankuPresenter.detailImpianku(idImpianku)
    }

    private fun getDataIntent() {
        idImpianku = intent.getStringExtra("id_impianku").toString()
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnBackToolbarLinear.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onSuccessGetDetailImpianku(
        impiankuListItem: List<ImpiankuItem>,
        pengeluaranHariListItem: List<PengeluaranHariItem>,
        msg: String
    ) {
        if (impiankuListItem.size > 0) {
            val impiankuItem = impiankuListItem.get(0)
            val price = impiankuItem.impiankuPrice.toInt()
            val money = impiankuItem.impiankuMoneyCollected.toInt()
            val days = impiankuItem.impiankuDays.toInt()
            val percent: Double = (money.toDouble() / price.toDouble()) * 100
            val priceD: Double = price.toDouble()
            val moneyD: Double = money.toDouble()
            val daysD: Double = days.toDouble()
            val moneyPerDaysD: Double = Math.ceil(priceD / daysD)
            val daysOnProgress: Double = Math.ceil(moneyD / moneyPerDaysD)
            val daysLeft: Int = days - daysOnProgress.toInt()

            binding.layoutContent.layoutDetailImpianku.visibility = View.VISIBLE

            binding.txtImpianku.text = impiankuItem.impiankuTitle
            binding.layoutContent.txtTitleImpianku.text = impiankuItem.impiankuTitle
            Glide.with(this)
                .load(Server.BASE_URL_IMG_IMPIANKU + impiankuItem.impiankuImg)
                .apply(RequestOptions().centerCrop())
                .into(binding.imgImpianku)

            if (impiankuItem.impiankuStatus.equals("Progress")) {
                binding.layoutContent.layoutStatusMengejar.visibility = View.VISIBLE
                binding.layoutContent.layoutStatusSelesai.visibility = View.GONE
            } else {
                binding.layoutContent.layoutStatusMengejar.visibility = View.GONE
                binding.layoutContent.layoutStatusSelesai.visibility = View.VISIBLE
            }

            if (daysLeft > impiankuItem.impiankuDays.toInt() || daysLeft < 0) {
                binding.layoutContent.txtDaysLeft.text = impiankuItem.impiankuDays + " Hari"
            } else if (daysLeft == 0) {
                binding.layoutContent.cvWaktuMenabung.visibility = View.GONE
            } else {
                binding.layoutContent.txtDaysLeft.text = daysLeft.toString() + " Hari"
            }
            binding.layoutContent.txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price)
            )
            binding.layoutContent.txtPercent.setText(percent.toInt().toString() + "%")
            binding.layoutContent.barImpianku.max = 100
            ObjectAnimator.ofInt(binding.layoutContent.barImpianku, "progress", percent.toInt())
                .setDuration(500)
                .start()

            binding.layoutContent.btnLinkShopee.setOnClickListener {
                if (impiankuItem.impiankuLinkShopee.length > 0) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(impiankuItem.impiankuLinkShopee)
                        )
                    )
                }
            }

            if (impiankuItem.impiankuLinkShopee.length > 0) {
                binding.layoutContent.btnLinkShopee.visibility = View.VISIBLE
            } else {
                binding.layoutContent.btnLinkShopee.visibility = View.GONE
            }

            impiankuDetailHariAdapter.delete()
            pengeluaranHariItem.clear()
            pengeluaranHariItem.addAll(pengeluaranHariListItem)
            impiankuDetailHariAdapter.notifyDataSetChanged()

            pd.dismiss()
        } else {
            onBackPressed()
        }
    }

    override fun onErrorGetDetailImpianku(msg: String) {
        impiankuPresenter.detailImpianku(idImpianku)
    }

    override fun onSuccessGetImpianku(
        progressListItem: List<ImpiankuProgressItem>,
        finishedListItem: List<ImpiankuFinishedItem>,
        msg: String
    ) {
    }

    override fun onErrorGetImpianku(msg: String) {}

    override fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String) {}

    override fun onErrorScrapper(msg: String) {}

    override fun onSuccessAddImpiankuShopee(msg: String) {}

    override fun onErrorAddImpiankuShopee(msg: String) {}

    override fun onSuccessAddImpiankuManual(msg: String) {}

    override fun onErrorAddImpiankuManual(msg: String) {}

    override fun onBackPressed() {
        pd.dismiss()
        super.onBackPressed()
    }
}