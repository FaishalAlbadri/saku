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
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.data.impianku.ImpiankuProgressItem
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quest_impianku, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataImpianku: ImpiankuProgressItem = listImpianku.get(position)

        Glide.with(context)
            .load(Server.BASE_URL_IMG_IMPIANKU + dataImpianku.impiankuImg)
            .apply(RequestOptions().centerCrop())
            .into(holder.imgImpianku)

        var price = dataImpianku.impiankuPrice.toInt()
        var money = dataImpianku.impiankuMoneyCollected.toInt()
        var percent: Double = (money.toDouble() / price.toDouble()) * 100
        var moneyPerDays = price / dataImpianku.impiankuDays.toInt()

        holder.txtImpianku.setText(dataImpianku.impiankuTitle)
        holder.txtPercent.setText(percent.toInt().toString() + "%")
        holder.txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price))
        holder.btnMenabung.setText(Util.currencyRupiah(moneyPerDays))

        holder.barImpianku.max = 100
        ObjectAnimator.ofInt(holder.barImpianku, "progress", percent.toInt()).setDuration(500)
            .start()

        holder.btnSkip.setOnClickListener {
            delete(position)
        }

        holder.btnMenabung.setOnClickListener {
            questImpiankuDialogFragment.updateQuest(dataImpianku.idImpianku, position)
        }
    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.img_impianku)
        lateinit var imgImpianku: ImageView

        @BindView(R.id.txt_percent)
        lateinit var txtPercent: TextView

        @BindView(R.id.txt_uang)
        lateinit var txtUang: TextView

        @BindView(R.id.txt_impianku)
        lateinit var txtImpianku: TextView

        @BindView(R.id.btn_menabung)
        lateinit var btnMenabung: TextView

        @BindView(R.id.btn_skip)
        lateinit var btnSkip: TextView

        @BindView(R.id.bar_impianku)
        lateinit var barImpianku: ProgressBar


        init {
            ButterKnife.bind(this, itemView)
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