package com.faishal.saku.adapter

import android.animation.ObjectAnimator
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
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.databinding.ItemImpiankuBinding
import com.faishal.saku.ui.impianku.ImpiankuDetailActivity
import com.faishal.saku.util.Util

class ImpiankuProgressAdapter : RecyclerView.Adapter<ImpiankuProgressAdapter.ViewHolder> {

    private var listImpianku: MutableList<ImpiankuProgressItem>
    private var context: Context

    constructor(context: Context, listImpianku: MutableList<ImpiankuProgressItem>) {
        this.listImpianku = listImpianku
        this.context = context
    }

    inner class ViewHolder(val binding: ItemImpiankuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImpiankuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataImpianku: ImpiankuProgressItem = listImpianku.get(position)
        with(holder) {
            binding.apply {
                Glide.with(context)
                    .load(Server.BASE_URL_IMG_IMPIANKU + dataImpianku.impiankuImg)
                    .apply(RequestOptions().centerCrop())
                    .into(imgImpianku)

                var price = dataImpianku.impiankuPrice.toInt()
                var money = dataImpianku.impiankuMoneyCollected.toInt()
                var percent: Double = (money.toDouble() / price.toDouble()) * 100

                txtImpianku.setText(dataImpianku.impiankuTitle)
                txtPercent.setText(percent.toInt().toString() + "%")
                txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price))

                barImpianku.max = 100
                ObjectAnimator.ofInt(barImpianku, "progress", percent.toInt()).setDuration(500).start()

                btnImpianku.setOnClickListener {
                    context.startActivity(Intent(context, ImpiankuDetailActivity::class.java)
                        .putExtra("id_impianku" , dataImpianku.idImpianku)
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listImpianku.size
    }

    fun delete() {
        val size: Int = listImpianku.size
        if (size > 0) {
            for (i in 0 until size) {
                listImpianku.removeAt(0)
            }
            notifyItemRangeChanged(0, size)
        }
    }
}