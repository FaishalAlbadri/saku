package com.faishal.saku.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.faishal.saku.R
import com.faishal.saku.api.Server
import com.faishal.saku.data.impianku.ImpiankuFinishedItem
import com.faishal.saku.ui.impianku.ImpiankuDetailActivity
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull

class ImpiankuFinishAdapter : RecyclerView.Adapter<ImpiankuFinishAdapter.ViewHolder> {

    private var listImpianku: MutableList<ImpiankuFinishedItem>
    private var context: Context

    constructor(context: Context, listImpianku: MutableList<ImpiankuFinishedItem>) {
        this.listImpianku = listImpianku
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_impianku, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataImpianku: ImpiankuFinishedItem = listImpianku.get(position)

        Glide.with(context)
            .load(Server.BASE_URL_IMG_IMPIANKU + dataImpianku.impiankuImg)
            .apply(RequestOptions().centerCrop())
            .into(holder.imgImpianku)

        var price = dataImpianku.impiankuPrice.toInt()
        var money = dataImpianku.impiankuMoneyCollected.toInt()
        var percent: Double = (money.toDouble() / price.toDouble()) * 100

        holder.txtImpianku.setText(dataImpianku.impiankuTitle)
        holder.txtPercent.setText(percent.toInt().toString() + "%")
        holder.txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price))

        holder.barImpianku.max = 100
        ObjectAnimator.ofInt(holder.barImpianku, "progress", percent.toInt()).setDuration(500).start()

        holder.btnImpianku.setOnClickListener {
            context.startActivity(
                Intent(context, ImpiankuDetailActivity::class.java)
                    .putExtra("id_impianku" , dataImpianku.idImpianku)
            )
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

        @BindView(R.id.btn_impianku)
        lateinit var btnImpianku: ConstraintLayout

        @BindView(R.id.bar_impianku)
        lateinit var barImpianku: ProgressBar


        init {
            ButterKnife.bind(this, itemView)
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