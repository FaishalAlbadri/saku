package com.faishal.saku.ui.impianku

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.di.ImpiankuRepositoryInject
import com.faishal.saku.presenter.impianku.ImpiankuContract
import com.faishal.saku.presenter.impianku.ImpiankuPresenter
import com.faishal.saku.ui.impianku.fragment.ScrapperDialogFragment
import com.faishal.saku.util.SessionManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ImpiankuActivity : BaseActivity(), ImpiankuContract.impiankuView {

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView

    @BindView(R.id.btn_fab)
    lateinit var btnFab: FloatingActionButton

    @BindView(R.id.btn_add_manual)
    lateinit var btnAddManual: FloatingActionButton

    @BindView(R.id.btn_add_shopee)
    lateinit var btnAddShopee: FloatingActionButton

    @BindView(R.id.cv_add_manual)
    lateinit var cvAddManual: CardView

    @BindView(R.id.cv_add_shopee)
    lateinit var cvAddShopee: CardView

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation
    private lateinit var fab_clock: Animation
    private lateinit var fab_anticlock: Animation
    private var isOpen: Boolean = false

    private lateinit var impiankuPresenter: ImpiankuPresenter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var scrapperDialogFragment: ScrapperDialogFragment

    private lateinit var sessionManager: SessionManager

    private lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impianku)
        ButterKnife.bind(this)

        setView()
    }

    private fun setView() {
        sessionManager = SessionManager(this)

        fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        fab_clock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_clock)
        fab_anticlock =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_anticlock)

        fragmentManager = supportFragmentManager

        impiankuPresenter = ImpiankuPresenter(ImpiankuRepositoryInject.provideTo(this))
        impiankuPresenter.onAttachView(this)

        scrapperDialogFragment = ScrapperDialogFragment.newInstance(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
    }

    @OnClick(R.id.btn_add_manual)
    fun onBtnAddManualClicked() {
        hideFAB()
    }

    @OnClick(R.id.btn_add_shopee)
    fun onBtnAddShopeeClicked() {
        scrapperDialogFragment.show(fragmentManager, "")
        hideFAB()
    }

    @OnClick(R.id.cv_add_manual)
    fun onCvAddManualClicked() {
        hideFAB()
    }

    @OnClick(R.id.cv_add_shopee)
    fun onCvAddShopeeClicked() {
        scrapperDialogFragment.show(fragmentManager, "")
        hideFAB()
    }

    @OnClick(R.id.btn_fab)
    fun onBtnFabClicked() {
        if (isOpen) {
            hideFAB()
        } else {
            showFAB()
        }
    }

    private fun showFAB() {
        cvAddManual.setClickable(true)
        cvAddShopee.setClickable(true)
        btnAddManual.setClickable(true)
        btnAddShopee.setClickable(true)
        cvAddManual.setVisibility(View.VISIBLE)
        cvAddShopee.setVisibility(View.VISIBLE)
        btnAddManual.setVisibility(View.VISIBLE)
        btnAddShopee.setVisibility(View.VISIBLE)
        cvAddManual.startAnimation(fab_open)
        cvAddShopee.startAnimation(fab_open)
        btnAddManual.startAnimation(fab_open)
        btnAddShopee.startAnimation(fab_open)
        btnFab.startAnimation(fab_clock)
        isOpen = true
    }

    private fun hideFAB() {
        cvAddManual.setClickable(false)
        cvAddShopee.setClickable(false)
        btnAddManual.setClickable(false)
        btnAddShopee.setClickable(false)
        cvAddManual.setVisibility(View.GONE)
        cvAddShopee.setVisibility(View.GONE)
        btnAddManual.setVisibility(View.GONE)
        btnAddShopee.setVisibility(View.GONE)
        cvAddManual.startAnimation(fab_close)
        cvAddShopee.startAnimation(fab_close)
        btnAddManual.startAnimation(fab_close)
        btnAddShopee.startAnimation(fab_close)
        btnFab.startAnimation(fab_anticlock)
        isOpen = false
    }

    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }

    override fun onSuccessScrapper(scrapperListItem: List<ScrapperItem>, msg: String) {
        val dataScrapper: ScrapperItem = scrapperListItem.get(0)
        scrapperDialogFragment.onSuccesData(
            dataScrapper.title,
            dataScrapper.harga.toString(),
            dataScrapper.image
        )
    }

    override fun onErrorScrapper(msg: String) {
        scrapperDialogFragment.clearData()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessAddImpiankuShopee(msg: String) {
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onErrorAddImpiankuShopee(msg: String) {
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun addImpiankuShopee(title: String, price: String, img: String, days: String, link: String) {
        pd.show()
        impiankuPresenter.addImpiankuShopee(sessionManager.getIdUser()!!, title, price, img, days, link)
    }

    fun loadScrapper(link: String) {
        impiankuPresenter.scrapper(link)

    }
}