package com.faishal.saku.repository.quest

import androidx.annotation.NonNull
import com.faishal.saku.data.impianku.ImpiankuProgressItem

interface QuestImpiankuDataResource {

    fun questImpianku(idUser: String, @NonNull questImpiankuGetCallback: QuestImpiankuGetCallback)

    fun updateQuestImpianku(idImpianku: String, @NonNull questImpiankuUpdateCallback: QuestImpiankuUpdateCallback)

    interface QuestImpiankuGetCallback {
        fun onSuccessGetQuestImpianku(
            progressListItem: List<ImpiankuProgressItem>,
            msg: String
        )

        fun onErrorGetQuestImpianku(msg: String)
    }

    interface QuestImpiankuUpdateCallback {
        fun onSuccessUpdateQuestImpianku(msg: String)

        fun onErrorUpdateQuestImpianku(msg: String)
    }
}