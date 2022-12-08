package com.faishal.saku.presenter.quest

import com.faishal.saku.data.impianku.ImpiankuProgressItem
import com.faishal.saku.repository.quest.QuestImpiankuDataResource
import com.faishal.saku.repository.quest.QuestImpiankuRepository

class QuestImpiankuPresenter : QuestImpiankuContract.questImpiankuPresenter {

    private var questImpiankuRepository: QuestImpiankuRepository
    private lateinit var questImpiankuView: QuestImpiankuContract.questImpiankuView

    constructor(questImpiankuRepository: QuestImpiankuRepository) {
        this.questImpiankuRepository = questImpiankuRepository
    }

    override fun questImpianku(idUser: String) {
        questImpiankuRepository.questImpianku(
            idUser,
            object : QuestImpiankuDataResource.QuestImpiankuGetCallback {
                override fun onSuccessGetQuestImpianku(
                    progressListItem: List<ImpiankuProgressItem>,
                    msg: String
                ) {
                    questImpiankuView.onSuccessGetQuestImpianku(progressListItem, msg)
                }

                override fun onErrorGetQuestImpianku(msg: String) {
                    questImpiankuView.onErrorGetQuestImpianku(msg)
                }

            })
    }

    override fun updateQuestImpianku(idImpianku: String) {
        questImpiankuRepository.updateQuestImpianku(
            idImpianku,
            object : QuestImpiankuDataResource.QuestImpiankuUpdateCallback {
                override fun onSuccessUpdateQuestImpianku(msg: String) {
                    questImpiankuView.onSuccessUpdateQuestImpianku(msg)
                }

                override fun onErrorUpdateQuestImpianku(msg: String) {
                    questImpiankuView.onErrorUpdateQuestImpianku(msg)
                }

            })
    }

    override fun onAttachView(view: QuestImpiankuContract.questImpiankuView) {
        questImpiankuView = view
    }

    override fun onDettachView() {
    }
}