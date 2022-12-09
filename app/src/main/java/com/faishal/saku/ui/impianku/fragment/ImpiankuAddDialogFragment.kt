package com.faishal.saku.ui.impianku.fragment

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.DialogFragment
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.ui.impianku.ImpiankuActivity
import com.faishal.saku.util.Util
import com.faishal.saku.util.Util.hideKeyboard
import com.rengwuxian.materialedittext.MaterialEditText
import java.io.File

class ImpiankuAddDialogFragment(impiankuActivity: ImpiankuActivity) : DialogFragment() {

    @BindView(R.id.btn_close)
    lateinit var btnClose: ImageView

    @BindView(R.id.btn_send)
    lateinit var btnSend: ImageView

    @BindView(R.id.btn_upload_image)
    lateinit var btnUploadImage: TextView

    @BindView(R.id.spinner_time)
    lateinit var spinnerTime: Spinner

    @BindView(R.id.img_produk)
    lateinit var imgProduk: ImageView

    @BindView(R.id.txt_menabung_value)
    lateinit var txtMenabungValue: TextView

    @BindView(R.id.edt_harga_produk)
    lateinit var edtHargaProduk: MaterialEditText

    @BindView(R.id.edt_nama_produk)
    lateinit var edtNamaProduk: MaterialEditText

    @BindView(R.id.edt_time)
    lateinit var edtTime: MaterialEditText

    private val impiankuActivity: ImpiankuActivity = impiankuActivity
    private var xHari: Int = 1
    private lateinit var bitmapAccount: Bitmap
    private lateinit var filepathimg: Uri
    private var isImageAvailable = false

    companion object {
        fun newInstance(impiankuActivity: ImpiankuActivity): ImpiankuAddDialogFragment {
            return ImpiankuAddDialogFragment(impiankuActivity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_impianku_add_dialog, container, false)
        ButterKnife.bind(this, v)
        spinnerSet()
        setView()
        edtNamaProduk.requestFocus()
        return v
    }

    private fun setView() {
        edtTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edtTime.text!!.toString().equals("") && !edtHargaProduk.text!!.toString()
                        .equals("")
                ) {
                    val zero = 0
                    if (edtTime.text.toString()
                            .toBigInteger() > zero.toBigInteger() && edtHargaProduk.text.toString()
                            .toBigInteger() > zero.toBigInteger()
                    ) {
                        var hargaPerHari =
                            edtHargaProduk.text.toString().toBigInteger() / (edtTime.text.toString()
                                .toBigInteger() * xHari.toBigInteger())
                        txtMenabungValue.setText(Util.currencyRupiah(hargaPerHari.toString()))
                    }
                } else {
                    txtMenabungValue.text = ""
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        edtHargaProduk.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!edtTime.text!!.toString().equals("") && !edtHargaProduk.text!!.toString()
                        .equals("")
                ) {
                    val zero = 0
                    if (edtTime.text.toString()
                            .toBigInteger() > zero.toBigInteger() && edtHargaProduk.text.toString()
                            .toBigInteger() > zero.toBigInteger()
                    ) {
                        val hargaProduk = edtHargaProduk.text.toString()
                        val time = edtTime.text.toString()
                        val hari = xHari
                        val hargaPerHari = Math.ceil(hargaProduk.toDouble() / (time.toInt() * hari))
                        txtMenabungValue.setText(Util.currencyRupiah(hargaPerHari.toInt()))
                    }
                } else {
                    txtMenabungValue.text = ""
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun spinnerSet() {
        val timeStringArray = resources.getStringArray(R.array.time)

        spinnerTime.adapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_dropdown_item,
            timeStringArray
        )
        spinnerTime.setSelection(0)

        spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 1) {
                    xHari = 7
                } else if (position == 2) {
                    xHari = 30
                } else if (position == 3) {
                    xHari = 365
                } else {
                    xHari = 1
                }
                txtMenabungValue.setText("")
                edtTime.setText(null)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    private fun selectImg() {
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1)
        } else {
            val intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (data != null && data.getData() != null && resultCode == RESULT_OK) {
                filepathimg = data.getData()!!
                isImageAvailable = true

                try {
                    bitmapAccount = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        filepathimg
                    )
                    imgProduk.setImageBitmap(bitmapAccount)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    @OnClick(R.id.img_produk)
    fun onImgProdukClicked() {
        impiankuActivity.requestStoragePermission()
        selectImg()
    }

    @OnClick(R.id.btn_upload_image)
    fun onBtnUploadImageClicked() {
        impiankuActivity.requestStoragePermission()
        selectImg()
    }


    @OnClick(R.id.btn_close)
    fun onBtnCloseClicked() {
        clearData()
        dismiss()
    }

    @OnClick(R.id.btn_send)
    fun onBtnSendClicked() {
        if (edtNamaProduk.text.toString().equals("") || edtTime.text.toString()
                .equals("") || edtHargaProduk.text.toString().equals("")
        ) {
            Toast.makeText(activity, "Data ada yang masih kosong!", Toast.LENGTH_SHORT).show()
        } else {
            if (isImageAvailable) {
                try {
                    val imgfile = File(Util.getImagePath(filepathimg, impiankuActivity))
                    if (imgfile.toString().equals("") || imgfile.toString().isEmpty()) {
                        Toast.makeText(activity, "Gambar tidak dapat di upload!", Toast.LENGTH_SHORT).show()
                    } else {
                        hideKeyboard()
                        xHari  = xHari * edtTime.text.toString().toInt()
                        impiankuActivity.addImpiankuManual(
                            edtNamaProduk.text.toString(),
                            edtHargaProduk.text.toString(),
                            imgfile,
                            xHari.toString()
                        )
                        clearData()
                        dismiss()
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, "Gambar tidak dapat di upload!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Wajib memilih gambar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearData() {
        edtNamaProduk.clearFocus()
        edtNamaProduk.setText("")
        edtTime.clearFocus()
        edtTime.setText("")
        edtHargaProduk.clearFocus()
        edtHargaProduk.setText("")
        isImageAvailable = false
        spinnerTime.setSelection(0)
    }

    override fun onStart() {
        super.onStart()
        var dialog: Dialog = getDialog()!!
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window!!.setLayout(width, height)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        return dialog
    }
}