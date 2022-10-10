package com.faishal.saku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.data.news.NewsItem
import com.faishal.saku.ui.berita.BeritaActivity
import com.faishal.saku.ui.berita.BeritaDetailActivity
import org.jetbrains.annotations.NotNull

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private var listNews: MutableList<NewsItem>
    private var context: Context

    constructor(context: Context, listNews: MutableList<NewsItem>) {
        this.listNews = listNews
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataNewsItem: NewsItem = listNews.get(position)
        holder.btnAllNews.setVisibility(View.GONE)

        Glide.with(context)
            .load(Server.BASE_URL_IMG_NEWS + dataNewsItem.newsImg)
            .apply(RequestOptions().centerCrop())
            .into(holder.imgNews)


        holder.txtJudul.setText(dataNewsItem.newsJudul)
        holder.txtDesc.setText(dataNewsItem.newsDesc)
        holder.txtDate.setText(dataNewsItem.newsCreate)

        holder.btnNews.setOnClickListener {
            context.startActivity(Intent(context, BeritaDetailActivity::class.java)
                .putExtra("id", dataNewsItem.idNews)
                .putExtra("judul", dataNewsItem.newsJudul)
                .putExtra("desc", dataNewsItem.newsDesc)
                .putExtra("img", dataNewsItem.newsImg)
                .putExtra("date", dataNewsItem.newsCreate)
            )
        }

        holder.btnAllNews.setOnClickListener {
            context.startActivity(Intent(context, BeritaActivity::class.java))
        }

        if (position == itemCount - 1) {
            holder.btnAllNews.setVisibility(View.VISIBLE)
        }
    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.img_news)
        lateinit var imgNews: ImageView

        @BindView(R.id.txt_date)
        lateinit var txtDate: TextView

        @BindView(R.id.txt_judul)
        lateinit var txtJudul: TextView

        @BindView(R.id.txt_desc)
        lateinit var txtDesc: TextView

        @BindView(R.id.btn_news)
        lateinit var btnNews: ConstraintLayout

        @BindView(R.id.btn_all_news)
        lateinit var btnAllNews: LinearLayout

        init {
            ButterKnife.bind(this, itemView)
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