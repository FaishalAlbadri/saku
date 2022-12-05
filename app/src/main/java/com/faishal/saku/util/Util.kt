package com.faishal.saku.util

import android.R
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.MediaStore.Audio
import android.provider.MediaStore.Video
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.io.File
import java.text.NumberFormat
import java.util.*
import java.util.regex.Pattern


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
        val number = string.toBigInteger()
        var numberS = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(number.toBigDecimal())
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

    fun getImagePath(uri: Uri, context: Context): String? {
        var realPath_1: String? = ""
        var isImageFromGoogleDrive = false
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if ("com.android.externalstorage.documents" == uri.authority) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        realPath_1 =
                            Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    } else {
                        val DIR_SEPORATOR = Pattern.compile("/")
                        val rv: MutableSet<String> = HashSet()
                        val rawExternalStorage = System.getenv("EXTERNAL_STORAGE")
                        val rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE")
                        val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET")
                        if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
                            if (TextUtils.isEmpty(rawExternalStorage)) {
                                rv.add("/storage/sdcard0")
                            } else {
                                rv.add(rawExternalStorage)
                            }
                        } else {
                            val rawUserId: String
                            if (VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN_MR1) {
                                rawUserId = ""
                            } else {
                                val path = Environment.getExternalStorageDirectory().absolutePath
                                val folders = DIR_SEPORATOR.split(path)
                                val lastFolder = folders[folders.size - 1]
                                var isDigit = false
                                try {
                                    Integer.valueOf(lastFolder)
                                    isDigit = true
                                } catch (ignored: NumberFormatException) {
                                }
                                rawUserId = if (isDigit) lastFolder else ""
                            }
                            if (TextUtils.isEmpty(rawUserId)) {
                                rv.add(rawEmulatedStorageTarget)
                            } else {
                                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
                            }
                        }
                        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
                            val rawSecondaryStorages =
                                rawSecondaryStoragesStr.split(File.pathSeparator).toTypedArray()
                            Collections.addAll(rv, *rawSecondaryStorages)
                        }
                        val temp = rv.toTypedArray()
                        for (i in temp.indices) {
                            val tempf = File(temp[i] + "/" + split[1])
                            if (tempf.exists()) {
                                realPath_1 = temp[i] + "/" + split[1]
                            }
                        }
                    }
                } else if ("com.android.providers.downloads.documents" == uri.authority) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris
                        .withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            java.lang.Long.valueOf(id)
                        )
                    var cursor: Cursor? = null
                    val column = "_data"
                    val projection = arrayOf(column)
                    try {
                        cursor = context.contentResolver.query(
                            contentUri, projection, null, null,
                            null
                        )
                        if (cursor != null && cursor.moveToFirst()) {
                            val column_index = cursor.getColumnIndexOrThrow(column)
                            realPath_1 = cursor.getString(column_index)
                        }
                    } finally {
                        cursor?.close()
                    }
                } else if ("com.android.providers.media.documents" == uri.authority) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":").toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    var cursor: Cursor? = null
                    val column = "_data"
                    val projection = arrayOf(column)
                    try {
                        cursor = context.contentResolver
                            .query(contentUri!!, projection, selection, selectionArgs, null)
                        if (cursor != null && cursor.moveToFirst()) {
                            val column_index = cursor.getColumnIndexOrThrow(column)
                            realPath_1 = cursor.getString(column_index)
                        }
                    } finally {
                        cursor?.close()
                    }
                } else if ("com.google.android.apps.docs.storage" == uri.authority) {
                    isImageFromGoogleDrive = true
                }
            }
        } else {
            if ("content".equals(uri.scheme, ignoreCase = true)) {
                var cursor: Cursor? = null
                val column = "_data"
                val projection = arrayOf(column)
                try {
                    cursor = context.contentResolver.query(uri, projection, null, null, null)
                    if (cursor != null && cursor.moveToFirst()) {
                        val column_index = cursor.getColumnIndexOrThrow(column)
                        realPath_1 = cursor.getString(column_index)
                    }
                } finally {
                    cursor?.close()
                }
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                realPath_1 = uri.path
            }
        }
        return realPath_1
    }
}