package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.faishal.saku.R
import com.faishal.saku.data.impianku.detail.PengeluaranHariItem
import com.faishal.saku.data.impianku.detail.PengeluaranItem
import org.jetbrains.annotations.NotNull

class ImpiankuDetailHariAdapter : RecyclerView.Adapter<ImpiankuDetailHariAdapter.ViewHolder> {

    private var context: Context
    private var listItem: MutableList<PengeluaranHariItem>
    private var listPengeluaranItem: ArrayList<PengeluaranItem> = ArrayList()
    private lateinit var impiankuDetailAdapter: ImpiankuDetailAdapter

    constructor(context: Context, list: MutableList<PengeluaranHariItem>) {
        this.context = context
        this.listItem = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pengeluaran_hari, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPengeluaranHariItem: PengeluaranHariItem = listItem.get(position)

        impiankuDetailAdapter = ImpiankuDetailAdapter(context, listPengeluaranItem)
        holder.rvPengeluaran.setLayoutManager(LinearLayoutManager(context))
        holder.rvPengeluaran.setAdapter(impiankuDetailAdapter)

        impiankuDetailAdapter.delete()
        listPengeluaranItem.clear()
        listPengeluaranItem.addAll(dataPengeluaranHariItem.pengeluaran)
        impiankuDetailAdapter.notifyDataSetChanged()

        holder.txtDate.setText(dataPengeluaranHariItem.catatanItemCreate)
    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.txt_date)
        lateinit var txtDate: TextView

        @BindView(R.id.rv_pengeluaran)
        lateinit var rvPengeluaran: RecyclerView

        init {
            ButterKnife.bind(this, itemView)
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