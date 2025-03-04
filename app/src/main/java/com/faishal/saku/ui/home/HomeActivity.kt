package com.faishal.saku.ui.home

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faishal.saku.R
import com.faishal.saku.adapter.HomeAdapter
import com.faishal.saku.adapter.NewsAdapter
import com.faishal.saku.api.Server
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.data.catatan.UserItem
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.databinding.ActivityHomeBinding
import com.faishal.saku.di.CatatanRepositoryInject
import com.faishal.saku.di.NewsRepositoryInject
import com.faishal.saku.di.QuestImpiankuRepositoryInject
import com.faishal.saku.presenter.catatan.CatatanContract
import com.faishal.saku.presenter.catatan.CatatanPresenter
import com.faishal.saku.presenter.impianku.ImpiankuPresenter
import com.faishal.saku.presenter.news.NewsContract
import com.faishal.saku.presenter.news.NewsPresenter
import com.faishal.saku.presenter.quest.QuestImpiankuContract
import com.faishal.saku.presenter.quest.QuestImpiankuPresenter
import com.faishal.saku.ui.aboutus.AboutUsActivity
import com.faishal.saku.ui.berita.BeritaActivity
import com.faishal.saku.ui.foodspin.FoodSpinWheelActivity
import com.faishal.saku.ui.home.fragment.AddCatatanFragment
import com.faishal.saku.ui.home.fragment.QuestImpiankuDialogFragment
import com.faishal.saku.ui.impianku.ImpiankuActivity
import com.faishal.saku.ui.profile.ProfileActivity
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import java.util.*


class HomeActivity : BaseActivity(), CatatanContract.catatanView, NewsContract.newsView,
    QuestImpiankuContract.questImpiankuView {

    private lateinit var pd: ProgressDialog
    private lateinit var quesImpiankuPresenter: QuestImpiankuPresenter
    private lateinit var catatanPresenter: CatatanPresenter
    private lateinit var newPresenter: NewsPresenter
    private lateinit var sessionManager: SessionManager
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var newsAdapter: NewsAdapter
    private var catatanItem: ArrayList<CatatanItem> = ArrayList()
    private var newsItem: ArrayList<NewsItem> = ArrayList()
    var questImpiankuItem: ArrayList<ImpiankuProgressItem> = ArrayList()

    private lateinit var fragmentManager: FragmentManager
    private lateinit var questImpiankuDialogFragment: QuestImpiankuDialogFragment

    private var mRewardedAd: RewardedAd? = null

    private var loadQuest = true
    private var positionDeleteQuest = 0

    private var _binding: ActivityHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
    }


    private fun setView() {
        sessionManager = SessionManager(this)

        fragmentManager = supportFragmentManager

        newsAdapter = NewsAdapter(this, newsItem)
        binding.rvNews.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
        binding.rvNews.setAdapter(newsAdapter)

        homeAdapter = HomeAdapter(this, catatanItem)
        binding.rvLaporan.setLayoutManager(LinearLayoutManager(this))
        binding.rvLaporan.setAdapter(homeAdapter)

        catatanPresenter = CatatanPresenter(CatatanRepositoryInject.provideTo(this))
        catatanPresenter.onAttachView(this)

        newPresenter = NewsPresenter(NewsRepositoryInject.provideTo(this))
        newPresenter.onAttachView(this)

        quesImpiankuPresenter =
            QuestImpiankuPresenter(QuestImpiankuRepositoryInject.provideTo(this))
        quesImpiankuPresenter.onAttachView(this)

        questImpiankuDialogFragment = QuestImpiankuDialogFragment.newInstance(this)


        newPresenter.news("6")

        Util.refreshColor(binding.refreshHome)
        binding.refreshHome.setOnRefreshListener {
            newPresenter.news("6")
        }

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        binding.apply {
            btnAdd.setOnClickListener {
                AddCatatanFragment.newInstance(this@HomeActivity).show(fragmentManager, "")
            }

            btnMore.setOnClickListener {
                val popupMenu = PopupMenu(this@HomeActivity, btnMore)
                popupMenu.menuInflater.inflate(R.menu.home, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item!!.itemId) {
                            R.id.aboutus -> startActivity(
                                Intent(
                                    applicationContext,
                                    AboutUsActivity::class.java
                                )
                            )
                        }
                        return true
                    }

                })
                popupMenu.show()
            }

            btnImpianku.setOnClickListener {
                startActivity(Intent(applicationContext, ImpiankuActivity::class.java))
            }

            btnNews.setOnClickListener {
                startActivity(Intent(applicationContext, BeritaActivity::class.java))
            }

            imgProfile.setOnClickListener {
                startActivity(Intent(applicationContext, ProfileActivity::class.java))
            }

            btnFood.setOnClickListener {
                startActivity(Intent(applicationContext, FoodSpinWheelActivity::class.java))
            }
        }
    }

    fun showAds(nominalPendapatan: String, monthCatatan: Int, yearCatatan: Int) {
        pd.show()

        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            this,
            Server.ADS_ID_UNIT_TEST,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                    pd.cancel()
                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    mRewardedAd = rewardedAd
                    pd.cancel()
                }
            })

        mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                mRewardedAd = null
                pd.cancel()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                mRewardedAd = null
                pd.cancel()
            }
        }

        if (mRewardedAd != null) {
            mRewardedAd?.show(this) { rewardItem ->
                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
                val date: String = yearCatatan.toString() + "-" + monthCatatan.toString() + "-01"
                pd.show()
                catatanPresenter.catatanAdd(sessionManager.getIdUser()!!, nominalPendapatan, date)
            }
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                showAds(nominalPendapatan, monthCatatan, yearCatatan)
            }, 3000)
        }
    }

    override fun onSuccessCatatan(
        catatanListItem: List<CatatanItem>,
        userListItem: List<UserItem>,
        dateS: String,
        msg: String
    ) {
        binding.apply {
            if (loadQuest) {
                pd.show()
                quesImpiankuPresenter.questImpianku(sessionManager.getIdUser()!!)
                loadQuest = false
            }
            refreshHome.setRefreshing(false)
            pd.cancel()
            txtDate.setText(dateS)
            txtUsername.setText("Hi... " + userListItem!!.get(0)!!.userName)
            homeAdapter.delete()
            catatanItem.clear()
            catatanItem.addAll(catatanListItem)
            homeAdapter.notifyDataSetChanged()
        }
    }

    override fun onBlankCatatan(userListItem: List<UserItem>, dateS: String, msg: String) {
        binding.apply {
            refreshHome.setRefreshing(false)
            pd.cancel()
            txtDate.setText(dateS)
            txtUsername.setText("Hi... " + userListItem!!.get(0)!!.userName)
        }
    }

    override fun onErrorCatatan(msg: String) {
        binding.refreshHome.setRefreshing(false)
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

    override fun onSuccessGetQuestImpianku(
        progressListItem: List<ImpiankuProgressItem>,
        msg: String
    ) {
        pd.dismiss()
        questImpiankuItem.clear()
        questImpiankuItem.addAll(progressListItem)
        if (questImpiankuItem.size > 0) {
            questImpiankuDialogFragment.show(fragmentManager, "")
        }
    }

    override fun onErrorGetQuestImpianku(msg: String) {
        pd.dismiss()
    }

    override fun onSuccessUpdateQuestImpianku(msg: String) {
        pd.dismiss()
        questImpiankuDialogFragment.deleteQuest(positionDeleteQuest)
    }

    override fun onErrorUpdateQuestImpianku(msg: String) {
        pd.dismiss()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    fun updateQuest(idImpianku: String, position: Int) {
        pd.show()
        positionDeleteQuest = position
        quesImpiankuPresenter.updateQuestImpianku(idImpianku)
    }
}