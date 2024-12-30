package com.icdominguez.icdominguez.master_meme.data.repository

import android.content.Context
import com.icdominguez.icdominguez.master_meme.domain.model.MemeTemplate
import com.icdominguez.icdominguez.master_meme.domain.repository.MemeTemplatesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemeTemplatesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): MemeTemplatesRepository {

    private companion object val MEME_TEMPLATES_FOLDER = "meme_templates"

    override suspend fun getMemeTemplates(): List<MemeTemplate> {
        return withContext(Dispatchers.IO) {
            context.assets.list(MEME_TEMPLATES_FOLDER)?.map { name ->
                MemeTemplate(name = "file:///android_asset/${MEME_TEMPLATES_FOLDER}/${name}")
            }.orEmpty()
        }
    }
}