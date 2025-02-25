package com.faishal.saku.ui.berita

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faishal.saku.R
import com.faishal.saku.adapter.NewsAdapter
import com.faishal.saku.adapter.NewsAllAdapter
import com.faishal.saku.base.BaseActivity
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.databinding.ActivityBeritaBinding
import com.faishal.saku.di.NewsRepositoryInject
import com.faishal.saku.presenter.news.NewsContract
import com.faishal.saku.presenter.news.NewsPresenter
import com.faishal.saku.util.Util

class BeritaActivity : BaseActivity(), NewsContract.newsView {

    private lateinit var pd: ProgressDialog
    private lateinit var newPresenter: NewsPresenter
    private lateinit var newsAdapter: NewsAllAdapter
    private var newsItem: ArrayList<NewsItem> = ArrayList()

    private var _binding: ActivityBeritaBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()
    }

    private fun setView() {
        newsAdapter = NewsAllAdapter(this, newsItem)
        binding.rvNews.setLayoutManager(LinearLayoutManager(this))
        binding.rvNews.setAdapter(newsAdapter)

        newPresenter = NewsPresenter(NewsRepositoryInject.provideTo(this))
        newPresenter.onAttachView(this)

        newPresenter.news("all")

        Util.refreshColor(binding.refreshNews)
        binding.refreshNews.setOnRefreshListener {
            newPresenter.news("all")
        }

        pd = ProgressDialog(this)
        pd.setCancelable(false)
        pd.setCanceledOnTouchOutside(false)
        pd.setMessage("Loading")
        pd.show()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onSuccessNews(newsListItem: List<NewsItem>, msg: String) {
        binding.refreshNews.setRefreshing(false)
        pd.cancel()
        newsAdapter.delete()
        newsItem.clear()
        newsItem.addAll(newsListItem)
        newsAdapter.notifyDataSetChanged()
    }

    override fun onErrorNews(msg: String) {
        binding.refreshNews.setRefreshing(false)
        pd.cancel()
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}