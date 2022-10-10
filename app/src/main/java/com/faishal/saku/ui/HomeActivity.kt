package com.faishal.saku.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.HomeAdapter
import com.faishal.saku.adapter.NewsAdapter
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
import com.faishal.saku.util.SessionManager
import com.faishal.saku.util.Util

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        setView()
    }

    private fun setView() {
        sessionManager = SessionManager(this)

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