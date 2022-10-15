package com.faishal.saku.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.faishal.saku.R
import com.faishal.saku.data.pengeluaran.PengeluaranItem
import com.faishal.saku.ui.pengeluaran.PengeluaranActivity
import com.faishal.saku.util.Util
import org.jetbrains.annotations.NotNull

class PengeluaranAdapter : RecyclerView.Adapter<PengeluaranAdapter.ViewHolder> {

    private var context: Context
    private var listItem: MutableList<PengeluaranItem>

    constructor(context: Context, listItem: MutableList<PengeluaranItem>) {
        this.context = context
        this.listItem = listItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pengeluaran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataPengeluaranItem: PengeluaranItem = listItem.get(position)
        holder.labelJam.setText(dataPengeluaranItem.catatanItemCreate)
        holder.labelDana.setText(dataPengeluaranItem.kategoriDanaNama)

        if (!dataPengeluaranItem.kategoriPokokNama.isEmpty()) {
            holder.cvLabelDanaPokok.setVisibility(View.VISIBLE)
            holder.labelDanaPokok.setText(dataPengeluaranItem.kategoriPokokNama)
        } else {
            holder.cvLabelDanaPokok.setVisibility(View.GONE)
        }

        holder.txtJudul.setText(dataPengeluaranItem.catatanItemDesc)
        holder.txtHarga.setText(Util.currencyRupiah(dataPengeluaranItem.catatanItemHarga))

        val popupMenu = PopupMenu(context, holder.btnMore)
        popupMenu.inflate(R.menu.pengeluaran)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.edit -> {
                        (context as PengeluaranActivity).showEditDialog(
                            dataPengeluaranItem.idCatatanItem,
                            dataPengeluaranItem.catatanItemHarga,
                            dataPengeluaranItem.catatanItemDesc
                        )
                        return true
                    }

                    R.id.delete -> {
                        (context as PengeluaranActivity).showDeleteDialog(
                            dataPengeluaranItem.idCatatanItem,
                            dataPengeluaranItem.catatanItemDesc
                        )
                        return true
                    }
                }
                return false
            }
        })

        holder.btnMore.setOnClickListener {
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                popupMenu.show()
            }
        }
    }

    class ViewHolder(@NotNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.label_jam)
        lateinit var labelJam: TextView

        @BindView(R.id.label_dana)
        lateinit var labelDana: TextView

        @BindView(R.id.label_dana_pokok)
        lateinit var labelDanaPokok: TextView

        @BindView(R.id.cv_label_dana_pokok)
        lateinit var cvLabelDanaPokok: CardView

        @BindView(R.id.btn_more)
        lateinit var btnMore: ImageView

        @BindView(R.id.txt_judul)
        lateinit var txtJudul: TextView

        @BindView(R.id.txt_harga)
        lateinit var txtHarga: TextView

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