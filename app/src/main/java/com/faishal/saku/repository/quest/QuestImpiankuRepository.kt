package com.faishal.saku.repository.quest

class QuestImpiankuRepository: QuestImpiankuDataResource {

    private var questImpiankuDataResource: QuestImpiankuDataResource

    constructor(questImpiankuDataResource: QuestImpiankuDataResource) {
        this.questImpiankuDataResource = questImpiankuDataResource
    }


    override fun questImpianku(
        idUser: String,
        questImpiankuGetCallback: QuestImpiankuDataResource.QuestImpiankuGetCallback
    ) {
        questImpiankuDataResource.questImpianku(idUser, questImpiankuGetCallback)
    }

    override fun updateQuestImpianku(
        idImpianku: String,
        questImpiankuUpdateCallback: QuestImpiankuDataResource.QuestImpiankuUpdateCallback
    ) {
        questImpiankuDataResource.updateQuestImpianku(idImpianku, questImpiankuUpdateCallback)
    }
}