package com.faishal.saku.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.faishal.saku.R
import com.faishal.saku.data.kategori.dana.KategoriDanaItem
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull

class PengeluaranKategoriDanaAdapter :
    RecyclerView.Adapter<PengeluaranKategoriDanaAdapter.ViewHolder> {

    private var listitem: MutableList<KategoriDanaItem>
    private var context: Context

    constructor(context: Context, listitem: MutableList<KategoriDanaItem>) {
        this.listitem = listitem
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_info_pengeluaran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataDanaItem: KategoriDanaItem = listitem.get(position)
        holder.txtJudul.setText(dataDanaItem.kategoriDanaNama)
        holder.txtPengeluaran.setText(Util.currencyRupiah(dataDanaItem.totalPengeluaran) + " / ")
        holder.txtMaxPengeluaran.setText(Util.currencyRupiah(dataDanaItem.maximalDana))
    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.txt_max_pengeluaran)
        lateinit var txtMaxPengeluaran: TextView

        @BindView(R.id.txt_judul)
        lateinit var txtJudul: TextView

        @BindView(R.id.txt_pengeluaran)
        lateinit var txtPengeluaran: TextView

        init {
            ButterKnife.bind(this, itemView)
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