package com.faishal.saku.ui.home

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.HomeAdapter
import com.faishal.saku.adapter.NewsAdapter
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.di.CatatanRepositoryInject
import com.faishal.saku.di.NewsRepositoryInject
import com.faishal.saku.presenter.catatan.CatatanContract
import com.faishal.saku.presenter.catatan.CatatanPresenter
import com.faishal.saku.presenter.news.NewsContract
import com.faishal.saku.presenter.news.NewsPresenter
import com.faishal.saku.ui.home.fragment.AddCatatanFragment
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.util.*


class HomeActivity : BaseActivity(), CatatanContract.catatanView, NewsContract.newsView {

    @BindView(R.id.txt_date)
    lateinit var txtDate: TextView

    @BindView(R.id.txt_username)
    lateinit var txtUsername: TextView

    @BindView(R.id.btn_logout)
    lateinit var btnLogout: ConstraintLayout

    @BindView(R.id.rv_laporan)
    lateinit var rvLaporan: RecyclerView

    @BindView(R.id.rv_news)
    lateinit var rvNews: RecyclerView

    @BindView(R.id.refresh_home)
    lateinit var refreshHome: SwipeRefreshLayout

    private lateinit var pd: ProgressDialog
    private lateinit var catatanPresenter: CatatanPresenter
    private lateinit var newPresenter: NewsPresenter
    private lateinit var sessionManager: SessionManager
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var newsAdapter: NewsAdapter
    private var catatanItem: ArrayList<CatatanItem> = ArrayList()
    private var newsItem: ArrayList<NewsItem> = ArrayList()

    private lateinit var fragmentManager: FragmentManager

    private var mRewardedAd: RewardedAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        setView()
    }


    private fun setView() {
        sessionManager = SessionManager(this)

        fragmentManager = supportFragmentManager

        newsAdapter = NewsAdapter(this, newsItem)
        rvNews.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
        rvNews.setAdapter(newsAdapter)

        homeAdapter = HomeAdapter(this, catatanItem)
        rvLaporan.setLayoutManager(LinearLayoutManager(this))
        rvLaporan.setAdapter(homeAdapter)

        catatanPresenter = CatatanPresenter(CatatanRepositoryInject.provideTo(this))
        catatanPresenter.onAttachView(this)

        newPresenter = NewsPresenter(NewsRepositoryInject.provideTo(this))
        newPresenter.onAttachView(this)

        newPresenter.news("6")

        Util.refreshColor(refreshHome)
        refreshHome.setOnRefreshListener {
            newPresenter.news("6")
        }

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()
    }

    @OnClick(R.id.btn_add)
    fun onBtnAddCatatanClicked() {
        AddCatatanFragment.newInstance(this).show(fragmentManager, "")
    }

     fun showAds(nominalPendapatan: String, monthCatatan: Int, yearCatatan: Int) {
         pd.show()
        val requestConfiguration = RequestConfiguration.Builder()
            .setTestDeviceIds(Arrays.asList("D61B5A1C7673180EF3911FAE549E35B8"))
            .build()
        MobileAds.setRequestConfiguration(requestConfiguration)

        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            this,
            Server.ADS_ID_UNIT,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                mRewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                mRewardedAd = null
            }
        }

        if (mRewardedAd != null) {
            mRewardedAd?.show(this) { rewardItem ->
                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
                val date: String = yearCatatan.toString() + "-" + monthCatatan.toString() + "-01"
                catatanPresenter.catatanAdd(sessionManager.getIdUser()!!, nominalPendapatan, date)
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                showAds(nominalPendapatan, monthCatatan, yearCatatan)
            }, 3000)
        }
    }

    @OnClick(R.id.btn_logout)
    fun onBtnLogoutClicked() {
        sessionManager.logout()
    }

    override fun onSuccessCatatan(
        catatanListItem: List<CatatanItem>,
        userListItem: List<UserItem>,
        dateS: String,
        msg: String
    ) {
        refreshHome.setRefreshing(false)
        pd.cancel()
        txtDate.setText(dateS)
        txtUsername.setText("Hi... " + userListItem!!.get(0)!!.userName)
        homeAdapter.delete()
        catatanItem.clear()
        catatanItem.addAll(catatanListItem)
        homeAdapter.notifyDataSetChanged()
    }

    override fun onErrorCatatan(msg: String) {
        refreshHome.setRefreshing(false)
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessAddCatatan(msg: String) {
        catatanPresenter.catatan(sessionManager.getIdUser()!!)
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onFailedAddCatatan(msg: String) {
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessNews(newsListItem: List<NewsItem>, msg: String) {
        catatanPresenter.catatan(sessionManager.getIdUser()!!)
        newsAdapter.delete()
        newsItem.clear()
        newsItem.addAll(newsListItem)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onErrorNews(msg: String) {
        newPresenter.news("5")
    }
}