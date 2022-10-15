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
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.ui.pengeluaran.PengeluaranActivity
import com.rengwuxian.materialedittext.MaterialEditText

class EditPengeluaranDialogFragment(
    pengeluaranActivity: PengeluaranActivity,
    idPengeluaran: String,
    nominalPengeluaran: String,
    descPengeluaran: String
) : DialogFragment() {

    @BindView(R.id.btn_close)
    lateinit var btnClose: ImageView

    @BindView(R.id.btn_send)
    lateinit var btnSend: ImageView

    @BindView(R.id.edt_nominal)
    lateinit var edtNominal: MaterialEditText

    @BindView(R.id.edt_desc)
    lateinit var edtDesc: MaterialEditText

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(R.layout.fragment_edit_pengeluaran_dialog, container, false)
        ButterKnife.bind(this, view)

        edtDesc.setText(descPengeluaran)
        edtNominal.setText(nominalPengeluaran)
        return view
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
        if (edtDesc.text.toString().isEmpty() || edtNominal.text.toString().isEmpty()) {
            Toast.makeText(activity, "Data Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show()
        } else {
            dismiss()
            pengeluaranActivity.editPengeluaran(idPengeluaran, edtNominal.text.toString(), edtDesc.text.toString())
        }
    }
}