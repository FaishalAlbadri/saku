package com.faishal.saku.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faishal.saku.R
import com.faishal.saku.data.catatan.CatatanItem
import com.faishal.saku.databinding.ItemCatatanBinding
import com.faishal.saku.ui.pengeluaran.PengeluaranActivity
import com.faishal.saku.util.Util


class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private var listCatatan: MutableList<CatatanItem>
    private var context: Context

    constructor(context: Context, listCatatan: MutableList<CatatanItem>) {
        this.listCatatan = listCatatan
        this.context = context
    }

    inner class ViewHolder(val binding: ItemCatatanBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                val dataCatatan: CatatanItem = listCatatan.get(position)

                txtDate.setText(dataCatatan.catatanWaktu)
                txtMoney.setText(
                    Util.pengeluarangaji(
                        dataCatatan.totalPengeluaran,
                        dataCatatan.catatanPendapatan
                    )
                )
                txtPercent.setText(
                    Util.pengeluarangajipercent(
                        dataCatatan.totalPengeluaran,
                        dataCatatan.catatanPendapatan
                    )
                )

                if (dataCatatan.catatanWaktu!!.startsWith("Januari")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_1_jan)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Februari")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_2_feb)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Maret")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_3_mar)
                } else if (dataCatatan.catatanWaktu!!.startsWith("April")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_4_apr)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Mei")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_5_mei)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Juni")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_6_jun)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Juli")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_7_jul)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Agustus")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_8_aug)
                } else if (dataCatatan.catatanWaktu!!.startsWith("September")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_9_sept)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Oktober")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_10_okt)
                } else if (dataCatatan.catatanWaktu!!.startsWith("November")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_11_nov)
                } else if (dataCatatan.catatanWaktu!!.startsWith("Desember")) {
                    btnCatatan.setBackgroundResource(R.drawable.card_12_des)
                }

                btnCatatan.setOnClickListener {
                    context.startActivity(Intent(context, PengeluaranActivity::class.java)
                        .putExtra("id_catatan", dataCatatan.idCatatan)
                        .putExtra("waktu", dataCatatan.catatanWaktu)
                    )
                }
            }
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