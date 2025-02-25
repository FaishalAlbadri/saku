package com.faishal.saku.ui.home.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.faishal.saku.R
import com.faishal.saku.databinding.FragmentAddCatatanBinding
import com.faishal.saku.ui.home.HomeActivity
import com.rengwuxian.materialedittext.MaterialEditText
import java.util.*

class AddCatatanFragment(homeActivity: HomeActivity) : DialogFragment() {


    private val homeActivity: HomeActivity = homeActivity
    private var monthCatatan: Int = 0
    private var yearCatatan: Int = 0

    companion object {
        fun newInstance(homeActivity: HomeActivity): AddCatatanFragment {
            return AddCatatanFragment(homeActivity)
        }
    }

    private var _binding: FragmentAddCatatanBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerSet()
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

        binding.apply {
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

            btnClose.setOnClickListener {
                dismiss()
            }

            btnSend.setOnClickListener {
                if (edtPendapatan.text.toString().isEmpty()) {
                    Toast.makeText(activity, "Nominal Pendapatan Masih Kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    dismiss()
                    homeActivity.showAds(edtPendapatan.text.toString(), monthCatatan, yearCatatan)
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
}