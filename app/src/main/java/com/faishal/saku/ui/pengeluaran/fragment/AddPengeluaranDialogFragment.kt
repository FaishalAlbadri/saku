package com.faishal.saku.ui.pengeluaran.fragment

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.faishal.saku.R
import com.faishal.saku.databinding.FragmentAddPengeluaranDialogBinding
import com.faishal.saku.ui.pengeluaran.PengeluaranActivity
import com.rengwuxian.materialedittext.MaterialEditText

class AddPengeluaranDialogFragment(pengeluaranActivity: PengeluaranActivity) : DialogFragment() {

    private var idPengeluaran = 0
    private var idPokok = 0

    private val pengeluaranActivity = pengeluaranActivity

    companion object {
        fun newInstance(pengeluaranActivity: PengeluaranActivity): AddPengeluaranDialogFragment {
            return AddPengeluaranDialogFragment(pengeluaranActivity)
        }
    }

    private var _binding: FragmentAddPengeluaranDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPengeluaranDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinneSet()

        binding.apply {
            btnSend.setOnClickListener {
                if (edtDesc.text.toString().isEmpty() || edtNominal.text.toString()
                        .isEmpty() || idPengeluaran == 0
                ) {
                    Toast.makeText(activity, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    if (idPengeluaran == 1 && idPokok == 0) {
                        Toast.makeText(activity, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
                    } else {
                        dismiss()
                        pengeluaranActivity.addPengeluaran(
                            idPengeluaran.toString(),
                            idPokok.toString(),
                            edtNominal.text.toString(),
                            edtDesc.text.toString()
                        )
                    }
                }
            }

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun spinneSet() {
        val pengeluaran = resources.getStringArray(R.array.kategori_pengeluaran)
        val pokok = resources.getStringArray(R.array.kategori_pokok)

        binding.apply {
            spinnerPengeluaran.adapter = ArrayAdapter(
                activity?.applicationContext!!,
                android.R.layout.simple_spinner_dropdown_item,
                pengeluaran
            )

            spinnerPokok.adapter = ArrayAdapter(
                activity?.applicationContext!!,
                android.R.layout.simple_spinner_dropdown_item,
                pokok
            )

            spinnerPengeluaran.setSelection(0)

            spinnerPengeluaran.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 1) {
                        txtPokok.setVisibility(View.VISIBLE)
                        spinnerPokok.setVisibility(View.VISIBLE)
                    } else {
                        txtPokok.setVisibility(View.GONE)
                        spinnerPokok.setVisibility(View.GONE)
                        spinnerPokok.setSelection(0)
                        idPokok = 0
                    }
                    idPengeluaran = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            spinnerPokok.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    idPokok = position
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
}