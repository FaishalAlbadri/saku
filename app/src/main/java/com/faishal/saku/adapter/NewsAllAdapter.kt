package com.faishal.saku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.databinding.ItemNewsAllBinding
import com.faishal.saku.ui.berita.BeritaDetailActivity


class NewsAllAdapter : RecyclerView.Adapter<NewsAllAdapter.ViewHolder> {

    private var listNews: MutableList<NewsItem>
    private var context: Context

    constructor(context: Context, listNews: MutableList<NewsItem>) {
        this.listNews = listNews
        this.context = context
    }

    inner class ViewHolder(val binding: ItemNewsAllBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataNewsItem: NewsItem = listNews.get(position)
        with(holder) {
            binding.apply {
                Glide.with(context)
                    .load(Server.BASE_URL_IMG_NEWS + dataNewsItem.newsImg)
                    .apply(RequestOptions().centerCrop())
                    .into(imgNews)


                txtJudul.setText(dataNewsItem.newsJudul)
                txtDesc.setText(dataNewsItem.newsDesc)
                txtDate.setText(dataNewsItem.newsCreate)

                btnNews.setOnClickListener {
                    context.startActivity(
                        Intent(context, BeritaDetailActivity::class.java)
                            .putExtra("id", dataNewsItem.idNews)
                            .putExtra("judul", dataNewsItem.newsJudul)
                            .putExtra("desc", dataNewsItem.newsDesc)
                            .putExtra("img", dataNewsItem.newsImg)
                            .putExtra("date", dataNewsItem.newsCreate)
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    fun delete() {
        val size: Int = listNews.size
        if (size > 0) {
            for (i in 0 until size) {
                listNews.removeAt(0)
            }
            notifyItemRangeChanged(0, size)
        }
    }
}