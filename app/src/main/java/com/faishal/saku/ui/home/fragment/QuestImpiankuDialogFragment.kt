package com.faishal.saku.ui.home.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.faishal.saku.R
import com.faishal.saku.adapter.QuestImpiankuAdapter
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.databinding.FragmentQuestImpiankuDialogBinding
import com.faishal.saku.ui.home.HomeActivity
import java.util.ArrayList

class QuestImpiankuDialogFragment(homeActivity: HomeActivity) : DialogFragment() {

    private val homeActivity: HomeActivity = homeActivity
    private var questImpiankuItem: ArrayList<ImpiankuProgressItem> = ArrayList()
    private lateinit var questImpiankuAdapter: QuestImpiankuAdapter

    companion object {
        fun newInstance(homeActivity: HomeActivity): QuestImpiankuDialogFragment {
            return QuestImpiankuDialogFragment(homeActivity)
        }
    }

    private var _binding: FragmentQuestImpiankuDialogBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuestImpiankuDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            questImpiankuAdapter = QuestImpiankuAdapter(
                requireActivity().applicationContext, questImpiankuItem,
                this@QuestImpiankuDialogFragment
            )
            rvQuest.setLayoutManager(LinearLayoutManager(activity))
            rvQuest.adapter = questImpiankuAdapter

            questImpiankuAdapter.delete()
            questImpiankuItem.clear()
            questImpiankuItem.addAll(homeActivity.questImpiankuItem)
            questImpiankuAdapter.notifyDataSetChanged()

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        var dialog: Dialog = getDialog()!!
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = getResources().getDisplayMetrics().heightPixels * 0.55

            dialog.window!!.setLayout(width, height.toInt())

            val back = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(back, 24, 24, 24, 24)
            dialog.window!!.setBackgroundDrawable(inset)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        return dialog
    }

    fun updateQuest(idImpianku: String, position: Int) {
        homeActivity.updateQuest(idImpianku, position)
    }

    fun deleteQuest(position: Int) {
        questImpiankuAdapter.delete(position)
    }

}