package com.faishal.saku.di

import android.content.Context
import com.faishal.saku.repository.quest.QuestImpiankuDataRemote
import com.faishal.saku.repository.quest.QuestImpiankuRepository

class QuestImpiankuRepositoryInject {
    companion object {
        fun provideTo(context: Context): QuestImpiankuRepository {
            return QuestImpiankuRepository(QuestImpiankuDataRemote(context))
        }
    }
}