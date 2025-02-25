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
import com.faishal.saku.R
import com.faishal.saku.adapter.ImpiankuFinishAdapter
import com.faishal.saku.adapter.ImpiankuProgressAdapter
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.impianku.detail.ImpiankuItem
import com.faishal.saku.data.impianku.detail.PengeluaranHariItem
import com.faishal.saku.data.scrapper.ScrapperItem
import com.faishal.saku.databinding.ActivityImpiankuBinding
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

    private var _binding: ActivityImpiankuBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityImpiankuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        sessionManager = SessionManager(this)

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        Util.refreshColor(binding.refreshImpianku)
        binding.refreshImpianku.setOnRefreshListener {
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
        binding.rvOnproggres.setLayoutManager(LinearLayoutManager(this))
        binding.rvOnproggres.setAdapter(impiankuProgressAdapter)

        impiankuFinishAdapter = ImpiankuFinishAdapter(this, impiankuFinishedItem)
        binding.rvFinished.setLayoutManager(LinearLayoutManager(this))
        binding.rvFinished.setAdapter(impiankuFinishAdapter)

        impiankuPresenter.impianku(sessionManager.getIdUser()!!)

        binding.apply {
            btnAddManual.setOnClickListener {
                impiankuAddDialogFragment.show(fragmentManager, "")
                hideFAB()
            }
            btnAddShopee.setOnClickListener {
                scrapperDialogFragment.show(fragmentManager, "")
                hideFAB()
            }
            cvAddManual.setOnClickListener {
                impiankuAddDialogFragment.show(fragmentManager, "")
                hideFAB()
            }
            cvAddShopee.setOnClickListener {
                scrapperDialogFragment.show(fragmentManager, "")
                hideFAB()
            }
            btnFab.setOnClickListener {
                if (isOpen) {
                    hideFAB()
                } else {
                    showFAB()
                }
            }
            btnBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun showFAB() {
        binding.apply {
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
    }

    private fun hideFAB() {
        binding.apply {
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
    }

    override fun onSuccessGetImpianku(
        progressListItem: List<ImpiankuProgressItem>,
        finishedListItem: List<ImpiankuFinishedItem>,
        msg: String
    ) {
        binding.apply {
            refreshImpianku.setRefreshing(false)
            pd.dismiss()
            val progressImpianku = progressListItem.size
            val finishImpianku = finishedListItem.size
            val total = progressImpianku + finishImpianku
            txtTotalValue.setText(total.toString())
            txtOnprogressValue.setText(progressImpianku.toString())
            txtFinishedValue.setText(finishImpianku.toString())

            if (progressImpianku > 0) {
                rvOnproggres.visibility = View.VISIBLE
                layoutBlankOnproggres.visibility = View.GONE
            } else {
                rvOnproggres.visibility = View.GONE
                layoutBlankOnproggres.visibility = View.VISIBLE
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
    }

    override fun onErrorGetImpianku(msg: String) {
        binding.refreshImpianku.setRefreshing(false)
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

    override fun onSuccessGetDetailImpianku(
        impiankuListItem: List<ImpiankuItem>,
        pengeluaranHariListItem: List<PengeluaranHariItem>,
        msg: String
    ) {}

    override fun onErrorGetDetailImpianku(msg: String) {}

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