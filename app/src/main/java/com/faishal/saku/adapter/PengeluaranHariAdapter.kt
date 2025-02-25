package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.faishal.saku.R
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.data.pengeluaran.PengeluaranItem
import com.faishal.saku.databinding.ItemPengeluaranHariBinding

class PengeluaranHariAdapter: RecyclerView.Adapter<PengeluaranHariAdapter.ViewHolder> {

    private var context: Context
    private var listItem: MutableList<PengeluaranHariItem>
    private var listPengeluaranItem: ArrayList<PengeluaranItem> = ArrayList()
    private lateinit var pengeluaranAdapter: PengeluaranAdapter

    constructor(context: Context, list: MutableList<PengeluaranHariItem>) {
        this.context = context
        this.listItem = list
    }

    inner class ViewHolder(val binding: ItemPengeluaranHariBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPengeluaranHariBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPengeluaranHariItem: PengeluaranHariItem = listItem.get(position)
        with(holder) {
            binding.apply {
                pengeluaranAdapter = PengeluaranAdapter(context, listPengeluaranItem)
                rvPengeluaran.setLayoutManager(LinearLayoutManager(context))
                rvPengeluaran.setAdapter(pengeluaranAdapter)

                pengeluaranAdapter.delete()
                listPengeluaranItem.clear()
                listPengeluaranItem.addAll(dataPengeluaranHariItem.pengeluaran)
                pengeluaranAdapter.notifyDataSetChanged()

                txtDate.setText(dataPengeluaranHariItem.catatanItemCreate)
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