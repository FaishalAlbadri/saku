package com.faishal.saku.ui.impianku.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.faishal.saku.R
import com.faishal.saku.databinding.FragmentScrapperDialogBinding
import com.faishal.saku.ui.impianku.ImpiankuActivity
import com.faishal.saku.util.Util
import com.faishal.saku.util.Util.hideKeyboard
import com.rengwuxian.materialedittext.MaterialEditText

class ScrapperDialogFragment(impiankuActivity: ImpiankuActivity) : DialogFragment() {

    private val impiankuActivity: ImpiankuActivity = impiankuActivity
    private var xHari: Int = 1
    private var hargaProduk: Int = 0
    private lateinit var linkshopee: String
    private lateinit var image: String

    companion object {
        fun newInstance(impiankuActivity: ImpiankuActivity): ScrapperDialogFragment {
            return ScrapperDialogFragment(impiankuActivity)
        }
    }

    private var _binding: FragmentScrapperDialogBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScrapperDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerSet()
        setView()
    }

    private fun setView() {
        binding.apply {
            edtTime.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!edtTime.text!!.toString().equals("")) {
                        if (edtTime.text.toString().toInt() > 0) {
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

            btnClose.setOnClickListener {
                clearData()
                dismiss()
            }

            btnSend.setOnClickListener {
                if (edtLinkShopee.text.toString().equals("") ||edtNamaProduk.text.toString().equals("") || edtTime.text.toString().equals("")) {
                    Toast.makeText(activity, "Data ada yang masih kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    hideKeyboard()
                    xHari  = xHari * edtTime.text.toString().toInt()
                    impiankuActivity.addImpiankuShopee(edtNamaProduk.text.toString(), hargaProduk.toString(), image, xHari.toString(), linkshopee)
                    clearData()
                    dismiss()
                }
            }

            btnGenerate.setOnClickListener {
                if (edtLinkShopee.text!!.isEmpty()) {
                    edtLinkShopee.setError("Link Kosong")
                    edtLinkShopee.requestFocus()
                } else {
                    hideKeyboard()
                    linkshopee = edtLinkShopee.text.toString()
                    imgLoading.setVisibility(View.VISIBLE)
                    layoutPreview.setVisibility(View.GONE)
                    edtTime.setText(null)
                    edtTime.setError(null)
                    edtTime.requestFocus()
                    txtMenabungValue.setText("")
                    txtHarga.setText("")
                    spinnerTime.setSelection(0)
                    impiankuActivity.loadScrapper(linkshopee)
                }
            }
        }
    }

    private fun spinnerSet() {
        binding.apply {
            val timeStringArray = resources.getStringArray(R.array.time)

            Glide.with(this@ScrapperDialogFragment)
                .load(R.raw.loading)
                .into(imgLoading)

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
                    edtTime.requestFocus()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }
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

    fun clearData() {
        binding.apply {
            layoutPreview.setVisibility(View.GONE)
            imgLoading.setVisibility(View.GONE)
            edtLinkShopee.setText(null)
            edtLinkShopee.setError(null)
            edtLinkShopee.requestFocus()
            edtTime.setText(null)
            edtTime.setError(null)
            edtTime.requestFocus()
            edtNamaProduk.setText(null)
            edtNamaProduk.setError(null)
            edtNamaProduk.requestFocus()
            txtMenabungValue.setText("")
            txtHarga.setText("")
            spinnerTime.setSelection(0)
        }
    }

    fun onSuccesData(nama: String, harga: String, image: String) {
        this.image = image
        binding.apply {
            layoutPreview.setVisibility(View.VISIBLE)
            imgLoading.setVisibility(View.GONE)
            Glide.with(this@ScrapperDialogFragment)
                .load(image)
                .apply(RequestOptions().centerCrop())
                .into(imgProduk)
            hargaProduk = harga.toInt()
            txtHarga.setText(Util.currencyRupiah(harga))
            edtNamaProduk.setText(nama)
        }
    }

}