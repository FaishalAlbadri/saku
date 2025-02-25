package com.faishal.saku.ui.pengeluaran.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.faishal.saku.R
import com.faishal.saku.databinding.FragmentEditPengeluaranDialogBinding
import com.faishal.saku.ui.pengeluaran.PengeluaranActivity
import com.rengwuxian.materialedittext.MaterialEditText

class EditPengeluaranDialogFragment(
    pengeluaranActivity: PengeluaranActivity,
    idPengeluaran: String,
    nominalPengeluaran: String,
    descPengeluaran: String
) : DialogFragment() {

    private val pengeluaranActivity = pengeluaranActivity

    private val nominalPengeluaran: String = nominalPengeluaran
    private val descPengeluaran: String = descPengeluaran
    private val idPengeluaran: String = idPengeluaran

    companion object {
        fun newInstance(
            pengeluaranActivity: PengeluaranActivity,
            idPengeluaran: String,
            nominalPengeluaran: String,
            descPengeluaran: String
        ): EditPengeluaranDialogFragment {
            return EditPengeluaranDialogFragment(
                pengeluaranActivity,
                idPengeluaran,
                nominalPengeluaran,
                descPengeluaran
            )
        }
    }

    private var _binding: FragmentEditPengeluaranDialogBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditPengeluaranDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            edtDesc.setText(descPengeluaran)
            edtNominal.setText(nominalPengeluaran)

            btnClose.setOnClickListener {
                dismiss()
            }

            btnSend.setOnClickListener {
                if (edtDesc.text.toString().isEmpty() || edtNominal.text.toString().isEmpty()) {
                    Toast.makeText(activity, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    dismiss()
                    pengeluaranActivity.editPengeluaran(idPengeluaran, edtNominal.text.toString(), edtDesc.text.toString())
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