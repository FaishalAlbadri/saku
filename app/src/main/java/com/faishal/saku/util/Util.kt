package com.faishal.saku.util

import android.R
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.text.NumberFormat
import java.util.*


object Util {

    fun fromHtml(html: String?): Spanned? {
        return if (html == null) {
            SpannableString("")
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
    }

    fun currencyRupiah(number: Int): String? {
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(number.toDouble())
    }

    fun currencyRupiah(string: String?): String? {
        val number = Integer.valueOf(string)
        return NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(number.toDouble())
    }

    fun pengeluarangaji(pengeluaran: String, pemasukan: String): String {
        return currencyRupiah(pengeluaran) + " / " + currencyRupiah(pemasukan)
    }

    fun pengeluarangajipercent(pengeluaran: String, pemasukan: String): String {
        val pengeluaranDouble: Double = pengeluaran.toDouble()
        val pemasukanDouble: Double = pemasukan.toDouble()
        val percent: Double = pengeluaranDouble / pemasukanDouble * 100
        return percent.toInt().toString() + "%"
    }

    fun refreshColor(refresh: SwipeRefreshLayout) {
        refresh.setColorSchemeResources(
            R.color.holo_blue_bright,
            R.color.holo_green_light,
            R.color.holo_orange_light,
            R.color.holo_red_light
        )
    }
}