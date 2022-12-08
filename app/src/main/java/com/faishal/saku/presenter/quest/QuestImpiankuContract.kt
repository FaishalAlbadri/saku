package com.faishal.saku.presenter.quest

import com.faishal.saku.base.BasePresenter
import com.faishal.saku.data.impianku.ImpiankuProgressItem

class QuestImpiankuContract {

    interface questImpiankuView {
        fun onSuccessGetQuestImpianku(
            progressListItem: List<ImpiankuProgressItem>,
            msg: String
        )
        fun onErrorGetQuestImpianku(msg: String)

        fun onSuccessUpdateQuestImpianku(msg: String)
        fun onErrorUpdateQuestImpianku(msg: String)
    }

    interface questImpiankuPresenter : BasePresenter<questImpiankuView> {
        fun questImpianku(idUser: String)
        fun updateQuestImpianku(idImpianku: String)
    }
}