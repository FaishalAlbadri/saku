package com.faishal.saku.ui.berita

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.NewsAdapter
import com.faishal.saku.adapter.NewsAllAdapter
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.di.NewsRepositoryInject
import com.faishal.saku.presenter.news.NewsContract
import com.faishal.saku.presenter.news.NewsPresenter
import com.faishal.saku.util.Util

class BeritaActivity : BaseActivity(), NewsContract.newsView {

    @BindView(R.id.btn_back)
    lateinit var btnBack: ImageView

    @BindView(R.id.rv_news)
    lateinit var rvNews: RecyclerView

    @BindView(R.id.refresh_news)
    lateinit var refreshNews: SwipeRefreshLayout

    private lateinit var pd: ProgressDialog
    private lateinit var newPresenter: NewsPresenter
    private lateinit var newsAdapter: NewsAllAdapter
    private var newsItem: ArrayList<NewsItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berita)
        ButterKnife.bind(this)

        setView()
    }

    private fun setView() {
        newsAdapter = NewsAllAdapter(this, newsItem)
        rvNews.setLayoutManager(LinearLayoutManager(this))
        rvNews.setAdapter(newsAdapter)

        newPresenter = NewsPresenter(NewsRepositoryInject.provideTo(this))
        newPresenter.onAttachView(this)

        newPresenter.news("all")

        Util.refreshColor(refreshNews)
        refreshNews.setOnRefreshListener {
            newPresenter.news("all")
        }

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()
    }

    @OnClick(R.id.btn_back)
    fun onBtnBackClicked() {
        onBackPressed()
    }

    override fun onSuccessNews(newsListItem: List<NewsItem>, msg: String) {
        refreshNews.setRefreshing(false)
        pd.cancel()
        newsAdapter.delete()
        newsItem.clear()
        newsItem.addAll(newsListItem)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onErrorNews(msg: String) {
        refreshNews.setRefreshing(false)
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}