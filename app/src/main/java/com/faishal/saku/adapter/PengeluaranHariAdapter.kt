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
import com.faishal.saku.data.pengeluaran.PengeluaranHariItem
import com.faishal.saku.data.pengeluaran.PengeluaranItem
import org.jetbrains.annotations.NotNull

class PengeluaranHariAdapter: RecyclerView.Adapter<PengeluaranHariAdapter.ViewHolder> {

    private var context: Context
    private var listItem: MutableList<PengeluaranHariItem>
    private var listPengeluaranItem: ArrayList<PengeluaranItem> = ArrayList()
    private lateinit var pengeluaranAdapter: PengeluaranAdapter

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

        pengeluaranAdapter = PengeluaranAdapter(context, listPengeluaranItem)
        holder.rvPengeluaran.setLayoutManager(LinearLayoutManager(context))
        holder.rvPengeluaran.setAdapter(pengeluaranAdapter)

        pengeluaranAdapter.delete()
        listPengeluaranItem.clear()
        listPengeluaranItem.addAll(dataPengeluaranHariItem.pengeluaran)
        pengeluaranAdapter.notifyDataSetChanged()

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