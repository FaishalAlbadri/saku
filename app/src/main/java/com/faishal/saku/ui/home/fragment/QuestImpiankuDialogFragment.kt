package com.faishal.saku.ui.home.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.faishal.saku.R
import com.faishal.saku.adapter.QuestImpiankuAdapter
import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.ui.home.HomeActivity
import java.util.ArrayList

class QuestImpiankuDialogFragment(homeActivity: HomeActivity) : DialogFragment() {

    @BindView(R.id.btn_close)
    lateinit var btnClose: ImageView

    @BindView(R.id.rv_quest)
    lateinit var rvQuest: RecyclerView

    private val homeActivity: HomeActivity = homeActivity
    private var questImpiankuItem: ArrayList<ImpiankuProgressItem> = ArrayList()
    private lateinit var questImpiankuAdapter: QuestImpiankuAdapter

    companion object {
        fun newInstance(homeActivity: HomeActivity): QuestImpiankuDialogFragment {
            return QuestImpiankuDialogFragment(homeActivity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quest_impianku_dialog, container, false)
        ButterKnife.bind(this, view)

        questImpiankuAdapter = QuestImpiankuAdapter(
            requireActivity().applicationContext, questImpiankuItem,
            this
        )
        rvQuest.setLayoutManager(LinearLayoutManager(activity))
        rvQuest.adapter = questImpiankuAdapter

        questImpiankuAdapter.delete()
        questImpiankuItem.clear()
        questImpiankuItem.addAll(homeActivity.questImpiankuItem)
        questImpiankuAdapter.notifyDataSetChanged()

        return view
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

    @OnClick(R.id.btn_close)
    fun onBtnCloseClicked() {
        dismiss()
    }

    fun updateQuest(idImpianku: String, position: Int) {
        homeActivity.updateQuest(idImpianku, position)
    }

    fun deleteQuest(position: Int) {
        questImpiankuAdapter.delete(position)
    }

}