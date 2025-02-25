package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faishal.saku.R
import com.faishal.saku.data.impianku.detail.PengeluaranItem
import com.faishal.saku.databinding.ItemPengeluaranBinding
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull

class ImpiankuDetailAdapter : RecyclerView.Adapter<ImpiankuDetailAdapter.ViewHolder> {

    private var context: Context
    private var listItem: MutableList<PengeluaranItem>

    constructor(context: Context, listItem: MutableList<PengeluaranItem>) {
        this.context = context
        this.listItem = listItem
    }

    inner class ViewHolder(val binding: ItemPengeluaranBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPengeluaranBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPengeluaranItem: PengeluaranItem = listItem.get(position)
        with(holder) {
            binding.apply {
                labelJam.setText(dataPengeluaranItem.catatanItemCreate)
                labelDana.setText(dataPengeluaranItem.kategoriDanaNama)

                cvLabelDanaPokok.setVisibility(View.GONE)
                btnMore.setVisibility(View.GONE)

                txtJudul.setText(dataPengeluaranItem.catatanItemDesc)
                txtHarga.setText(Util.currencyRupiah(dataPengeluaranItem.catatanItemHarga))
            }
        }
    }


    override fun getItemCount(): Int {
        return listItem.size
    }

    fun delete() {
        val size: Int = listItem.size
        if (size > 0) {
            for (i in 0 until size) {
                listItem.removeAt(0)
            }
            notifyItemRangeChanged(0, size)
        }
    }
}