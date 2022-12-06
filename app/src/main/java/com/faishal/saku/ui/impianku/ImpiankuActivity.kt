package com.faishal.saku.ui.impianku

import android.Manifest.permission
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.ImpiankuFinishAdapter
import com.faishal.saku.adapter.ImpiankuProgressAdapter
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.di.ImpiankuRepositoryInject
import com.faishal.saku.presenter.impianku.ImpiankuContract
import com.faishal.saku.presenter.impianku.ImpiankuPresenter
import com.faishal.saku.ui.impianku.fragment.ImpiankuAddDialogFragment
import com.faishal.saku.ui.impianku.fragment.ScrapperDialogFragment
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File


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

    @BindView(R.id.txt_total_value)
    lateinit var txtTotalValue: TextView

    @BindView(R.id.txt_onprogress_value)
    lateinit var txtOnprogressValue: TextView

    @BindView(R.id.txt_finished_value)
    lateinit var txtFinishedValue: TextView

    @BindView(R.id.rv_onproggres)
    lateinit var rvProggres: RecyclerView

    @BindView(R.id.rv_finished)
    lateinit var rvFinished: RecyclerView

    @BindView(R.id.layout_blank_onproggres)
    lateinit var layoutBlankProggres: LinearLayout

    @BindView(R.id.layout_blank_finished)
    lateinit var layoutBlankFinished: LinearLayout

    @BindView(R.id.refresh_impianku)
    lateinit var refreshImpianku: SwipeRefreshLayout

    private lateinit var impiankuProgressAdapter: ImpiankuProgressAdapter
    private lateinit var impiankuFinishAdapter: ImpiankuFinishAdapter
    private var impiankuProgressItem: ArrayList<ImpiankuProgressItem> = ArrayList()
    private var impiankuFinishedItem: ArrayList<ImpiankuFinishedItem> = ArrayList()

    private lateinit var fab_open: Animation
    private lateinit var fab_close: Animation
    private lateinit var fab_clock: Animation
    private lateinit var fab_anticlock: Animation
    private var isOpen: Boolean = false

    private lateinit var impiankuPresenter: ImpiankuPresenter
    private lateinit var fragmentManager: FragmentManager
    private lateinit var scrapperDialogFragment: ScrapperDialogFragment
    private lateinit var impiankuAddDialogFragment: ImpiankuAddDialogFragment

    private lateinit var sessionManager: SessionManager

    private lateinit var pd: ProgressDialog

    private val STORAGE_PERMISSION_CODE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impianku)
        ButterKnife.bind(this)

        setView()
    }

    private fun setView() {
        sessionManager = SessionManager(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        Util.refreshColor(refreshImpianku)
        refreshImpianku.setOnRefreshListener {
            impiankuPresenter.impianku(sessionManager.getIdUser()!!)
        }

        fab_open = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close)
        fab_clock = AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_clock)
        fab_anticlock =
            AnimationUtils.loadAnimation(applicationContext, R.anim.fab_rotate_anticlock)

        fragmentManager = supportFragmentManager

        impiankuPresenter = ImpiankuPresenter(ImpiankuRepositoryInject.provideTo(this))
        impiankuPresenter.onAttachView(this)

        scrapperDialogFragment = ScrapperDialogFragment.newInstance(this)
        impiankuAddDialogFragment = ImpiankuAddDialogFragment.newInstance(this)

        impiankuProgressAdapter = ImpiankuProgressAdapter(this, impiankuProgressItem)
        rvProggres.setLayoutManager(LinearLayoutManager(this))
        rvProggres.setAdapter(impiankuProgressAdapter)

        impiankuFinishAdapter = ImpiankuFinishAdapter(this, impiankuFinishedItem)
        rvFinished.setLayoutManager(LinearLayoutManager(this))
        rvFinished.setAdapter(impiankuFinishAdapter)

        impiankuPresenter.impianku(sessionManager.getIdUser()!!)
    }

    @OnClick(R.id.btn_add_manual)
    fun onBtnAddManualClicked() {
        impiankuAddDialogFragment.show(fragmentManager, "")
        hideFAB()
    }

    @OnClick(R.id.btn_add_shopee)
    fun onBtnAddShopeeClicked() {
        scrapperDialogFragment.show(fragmentManager, "")
        hideFAB()
    }

    @OnClick(R.id.cv_add_manual)
    fun onCvAddManualClicked() {
        impiankuAddDialogFragment.show(fragmentManager, "")
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

    override fun onSuccessGetImpianku(
        progressListItem: List<ImpiankuProgressItem>,
        finishedListItem: List<ImpiankuFinishedItem>,
        msg: String
    ) {
        refreshImpianku.setRefreshing(false)
        pd.dismiss()
        val progressImpianku = progressListItem.size
        val finishImpianku = finishedListItem.size
        val total = progressImpianku + finishImpianku
        txtTotalValue.setText(total.toString())
        txtOnprogressValue.setText(progressImpianku.toString())
        txtFinishedValue.setText(finishImpianku.toString())

        if (progressImpianku > 0) {
            rvProggres.visibility = View.VISIBLE
            layoutBlankProggres.visibility = View.GONE
        } else {
            rvProggres.visibility = View.GONE
            layoutBlankProggres.visibility = View.VISIBLE
        }

        if (finishImpianku > 0) {
            rvFinished.visibility = View.VISIBLE
            layoutBlankFinished.visibility = View.GONE
        } else {
            rvFinished.visibility = View.GONE
            layoutBlankFinished.visibility = View.VISIBLE
        }

        impiankuProgressAdapter.delete()
        impiankuProgressItem.clear()
        impiankuProgressItem.addAll(progressListItem)
        impiankuProgressAdapter.notifyDataSetChanged()

        impiankuFinishAdapter.delete()
        impiankuFinishedItem.clear()
        impiankuFinishedItem.addAll(finishedListItem)
        impiankuFinishAdapter.notifyDataSetChanged()
    }

    override fun onErrorGetImpianku(msg: String) {
        refreshImpianku.setRefreshing(false)
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
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
        impiankuPresenter.impianku(sessionManager.getIdUser()!!)
    }

    override fun onErrorAddImpiankuShopee(msg: String) {
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessAddImpiankuManual(msg: String) {
        impiankuPresenter.impianku(sessionManager.getIdUser()!!)
    }

    override fun onErrorAddImpiankuManual(msg: String) {
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun addImpiankuShopee(title: String, price: String, img: String, days: String, link: String) {
        pd.show()
        impiankuPresenter.addImpiankuShopee(
            sessionManager.getIdUser()!!,
            title, price, img, days, link
        )
    }

    fun addImpiankuManual(title: String, price: String, img: File, days: String) {
        pd.show()
        impiankuPresenter.addImpiankuManual(
            sessionManager.getIdUser()!!,
            title, price, img, days
        )
    }

    fun loadScrapper(link: String) {
        impiankuPresenter.scrapper(link)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestStoragePermission()
            }
        }
    }

    fun requestStoragePermission() {
        if (ContextCompat
                .checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (ActivityCompat
                .shouldShowRequestPermissionRationale(
                    this,
                    permission.READ_EXTERNAL_STORAGE
                )
        ) {
        }
        ActivityCompat
            .requestPermissions(
                this, arrayOf(permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
    }
}