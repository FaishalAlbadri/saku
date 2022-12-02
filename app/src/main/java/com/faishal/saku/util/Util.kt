package com.faishal.saku.util

import android.R
import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
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

    fun currencyRupiah(number: Int): String {
        var numberS = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(number.toDouble())
        if (numberS.contains(",")) {
            numberS = numberS.dropLast(3)
        }
        return numberS
    }

    fun currencyRupiah(string: String): String {
        val number = string.toInt()
        var numberS = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(number.toDouble())
        if (numberS.contains(",")) {
            numberS = numberS.dropLast(3)
        }
        return numberS
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

    fun DialogFragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}