package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faishal.saku.R
import com.faishal.saku.data.kategori.dana.KategoriDanaItem
import com.faishal.saku.databinding.ItemInfoPengeluaranBinding
import com.faishal.saku.util.Util

class PengeluaranKategoriDanaAdapter :
    RecyclerView.Adapter<PengeluaranKategoriDanaAdapter.ViewHolder> {

    private var listitem: MutableList<KategoriDanaItem>
    private var context: Context

    constructor(context: Context, listitem: MutableList<KategoriDanaItem>) {
        this.listitem = listitem
        this.context = context
    }

    inner class ViewHolder(val binding: ItemInfoPengeluaranBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInfoPengeluaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataDanaItem: KategoriDanaItem = listitem.get(position)
        with(holder) {
            binding.apply {
                txtJudul.setText(dataDanaItem.kategoriDanaNama)
                txtPengeluaran.setText(Util.currencyRupiah(dataDanaItem.totalPengeluaran) + " / ")
                txtMaxPengeluaran.setText(Util.currencyRupiah(dataDanaItem.maximalDana))
            }
        }
    }

    override fun getItemCount(): Int {
        return listitem.size
    }

    fun delete() {
        val size: Int = listitem.size
        if (size > 0) {
            for (i in 0 until size) {
                listitem.removeAt(0)
            }
            notifyItemRangeChanged(0, size)
        }
    }
}