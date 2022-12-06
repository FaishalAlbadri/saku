package com.faishal.saku.adapter

import android.animation.ObjectAnimator
import android.content.Context
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
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull

class ImpiankuProgressAdapter : RecyclerView.Adapter<ImpiankuProgressAdapter.ViewHolder> {

    private var listImpianku: MutableList<ImpiankuProgressItem>
    private var context: Context

    constructor(context: Context, listImpianku: MutableList<ImpiankuProgressItem>) {
        this.listImpianku = listImpianku
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_impianku, parent, false)
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

        holder.txtImpianku.setText(dataImpianku.impiankuTitle)
        holder.txtPercent.setText(percent.toInt().toString() + "%")
        holder.txtUang.setText(Util.currencyRupiah(money) + " / " + Util.currencyRupiah(price))

        holder.barImpianku.max = 100
        ObjectAnimator.ofInt(holder.barImpianku, "progress", percent.toInt()).setDuration(500).start()

        holder.btnImpianku.setOnClickListener {
            Toast.makeText(context, dataImpianku.impiankuTitle, Toast.LENGTH_SHORT).show()
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