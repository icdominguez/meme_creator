package com.icdominguez.icdominguez.master_meme.domain.repository

import com.icdominguez.icdominguez.master_meme.domain.model.MemeTemplate

interface MemeTemplatesRepository {
    suspend fun getMemeTemplates(): List<MemeTemplate>
}