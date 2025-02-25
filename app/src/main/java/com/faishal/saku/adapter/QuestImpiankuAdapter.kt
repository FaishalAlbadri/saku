package com.faishal.saku.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.databinding.ItemQuestImpiankuBinding
import com.faishal.saku.ui.home.fragment.QuestImpiankuDialogFragment
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull


class QuestImpiankuAdapter : RecyclerView.Adapter<QuestImpiankuAdapter.ViewHolder> {

    private var listImpianku: MutableList<ImpiankuProgressItem>
    private var context: Context
    private var questImpiankuDialogFragment: QuestImpiankuDialogFragment

    constructor(context: Context, listImpianku: MutableList<ImpiankuProgressItem> , questImpiankuDialogFragment: QuestImpiankuDialogFragment) {
        this.listImpianku = listImpianku
        this.context = context
        this.questImpiankuDialogFragment = questImpiankuDialogFragment
    }

    inner class ViewHolder(val binding: ItemQuestImpiankuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuestImpiankuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                var moneyPerDays = Math.ceil(price.toDouble() / dataImpianku.impiankuDays.toDouble())

                txtImpianku.setText(dataImpianku.impiankuTitle)
                txtPercent.setText(percent.toInt().toString() + "%")
                txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price))
                btnMenabung.setText(Util.currencyRupiah(moneyPerDays.toInt()))

                barImpianku.max = 100
                ObjectAnimator.ofInt(barImpianku, "progress", percent.toInt()).setDuration(500)
                    .start()

                btnSkip.setOnClickListener {
                    delete(position)
                }

                btnMenabung.setOnClickListener {
                    questImpiankuDialogFragment.updateQuest(dataImpianku.idImpianku, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listImpianku.size
    }

    fun delete(position: Int) {
        val size: Int = listImpianku.size
        if (size > 0) {
            listImpianku.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, size)
            notifyDataSetChanged()

            if (listImpianku.size == 0) {
                questImpiankuDialogFragment.dismiss()
            }
        }
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