package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.faishal.saku.R
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private var listCatatan: MutableList<CatatanItem>
    private var context: Context

    constructor(context: Context, listCatatan: MutableList<CatatanItem>) {
        this.listCatatan = listCatatan
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catatan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataCatatan: CatatanItem = listCatatan.get(position)

        holder.txtDate.setText(dataCatatan.catatanWaktu)
        holder.txtMoney.setText(
            Util.pengeluarangaji(
                dataCatatan.totalPengeluaran,
                dataCatatan.catatanPendapatan
            )
        )
        holder.txtPercent.setText(
            Util.pengeluarangajipercent(
                dataCatatan.totalPengeluaran,
                dataCatatan.catatanPendapatan
            )
        )

        if (dataCatatan.catatanWaktu!!.startsWith("Januari")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_1_jan)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Februari")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_2_feb)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Maret")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_3_mar)
        } else if (dataCatatan.catatanWaktu!!.startsWith("April")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_4_apr)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Mei")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_5_mei)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Juni")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_6_jun)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Juli")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_7_jul)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Agustus")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_8_aug)
        } else if (dataCatatan.catatanWaktu!!.startsWith("September")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_9_sept)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Oktober")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_10_okt)
        } else if (dataCatatan.catatanWaktu!!.startsWith("November")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_11_nov)
        } else if (dataCatatan.catatanWaktu!!.startsWith("Desember")) {
            holder.btnCatatan.setBackgroundResource(R.drawable.card_12_des)
        }

        holder.btnCatatan.setOnClickListener {
            Toast.makeText(context, dataCatatan.catatanWaktu, Toast.LENGTH_SHORT).show()
        }


    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.txt_date)
        lateinit var txtDate: TextView

        @BindView(R.id.txt_money)
        lateinit var txtMoney: TextView

        @BindView(R.id.txt_percent)
        lateinit var txtPercent: TextView

        @BindView(R.id.btn_catatan)
        lateinit var btnCatatan: ConstraintLayout

        init {
            ButterKnife.bind(this, itemView)
        }
    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }

    fun delete() {
        val size: Int = listCatatan.size
        if (size > 0) {
            for (i in 0 until size) {
                listCatatan.removeAt(0)
            }
            notifyItemRangeChanged(0, size)
        }
    }
}