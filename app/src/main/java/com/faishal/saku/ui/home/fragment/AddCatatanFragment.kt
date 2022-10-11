package com.faishal.saku.ui.home.fragment

import android.app.Dialog
import android.os.Bundle
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
import com.faishal.saku.ui.home.HomeActivity
import com.rengwuxian.materialedittext.MaterialEditText
import java.util.*

class AddCatatanFragment(homeActivity: HomeActivity) : DialogFragment() {

    @BindView(R.id.edt_pendapatan)
    lateinit var edtPendapatan: MaterialEditText

    @BindView(R.id.spinner_month)
    lateinit var spinnerMonth: Spinner

    @BindView(R.id.spinner_year)
    lateinit var spinnerYear: Spinner

    @BindView(R.id.btn_close)
    lateinit var btnClose: ImageView

    @BindView(R.id.btn_send)
    lateinit var btnSend: ImageView

    private val homeActivity: HomeActivity = homeActivity
    private var monthCatatan: Int = 0
    private var yearCatatan: Int = 0

    companion object {
        fun newInstance(homeActivity: HomeActivity): AddCatatanFragment {
            return AddCatatanFragment(homeActivity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_catatan, container, false)
        ButterKnife.bind(this, view)

        spinnerSet()

        return view
    }

    private fun spinnerSet() {
        val bulanStringArray = resources.getStringArray(R.array.month)
        val tahunStringArray = resources.getStringArray(R.array.year)

        val calendar: Calendar = Calendar.getInstance()
        val thisYear: Int = calendar.get(Calendar.YEAR)
        val thisMonth: Int = calendar.get(Calendar.MONTH)

        monthCatatan = thisMonth + 1
        yearCatatan = thisYear

        val positionYear = thisYear - 2020

        spinnerMonth.adapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_dropdown_item,
            bulanStringArray
        )
        spinnerYear.adapter = ArrayAdapter(
            activity?.applicationContext!!,
            android.R.layout.simple_spinner_dropdown_item,
            tahunStringArray
        )
        spinnerMonth.setSelection(thisMonth)
        spinnerYear.setSelection(positionYear)

        spinnerMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                monthCatatan = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val yearS: String = tahunStringArray[position]
                yearCatatan = yearS.toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

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

    @OnClick(R.id.btn_close)
    fun onBtnCloseClicked() {
        dismiss()
    }

    @OnClick(R.id.btn_send)
    fun onBtnSendClicked() {
        if (edtPendapatan.text.toString().isEmpty()) {
            Toast.makeText(activity, "Nominal Pendapatan Masih Kosong!", Toast.LENGTH_SHORT).show()
        } else {
            dismiss()
            homeActivity.showAds(edtPendapatan.text.toString(), monthCatatan, yearCatatan)
        }
    }
}