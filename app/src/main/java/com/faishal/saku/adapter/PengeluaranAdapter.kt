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
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.faishal.saku.R
import com.faishal.saku.data.pengeluaran.PengeluaranItem
import com.faishal.saku.databinding.ItemPengeluaranBinding
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

                if (!dataPengeluaranItem.kategoriPokokNama.isEmpty()) {
                    cvLabelDanaPokok.setVisibility(View.VISIBLE)
                    labelDanaPokok.setText(dataPengeluaranItem.kategoriPokokNama)
                } else {
                    cvLabelDanaPokok.setVisibility(View.GONE)
                }

                txtJudul.setText(dataPengeluaranItem.catatanItemDesc)
                txtHarga.setText(Util.currencyRupiah(dataPengeluaranItem.catatanItemHarga))

                val popupMenu = PopupMenu(context, btnMore)
                popupMenu.inflate(R.menu.pengeluaran)
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem): Boolean {
                        when (item.itemId) {
                            R.id.edit -> {
                                if (dataPengeluaranItem.idImpianku.equals("0")) {
                                    (context as PengeluaranActivity).showEditDialog(
                                        dataPengeluaranItem.idCatatanItem,
                                        dataPengeluaranItem.catatanItemHarga,
                                        dataPengeluaranItem.catatanItemDesc
                                    )
                                } else {
                                    Toast.makeText(context, "Data tidak bisa di edit", Toast.LENGTH_SHORT).show()
                                }
                                return true
                            }

                            R.id.delete -> {
                                if (dataPengeluaranItem.idImpianku.equals("0")) {
                                    (context as PengeluaranActivity).showDeleteDialog(
                                        dataPengeluaranItem.idCatatanItem,
                                        dataPengeluaranItem.catatanItemDesc
                                    )
                                }else {
                                    Toast.makeText(context, "Data tidak bisa di hapus", Toast.LENGTH_SHORT).show()
                                }
                                return true
                            }
                        }
                        return false
                    }
                })

                btnMore.setOnClickListener {
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