package com.icdominguez.icdominguez.master_meme.domain.repository

import com.icdominguez.icdominguez.master_meme.domain.model.MemeEntity
import kotlinx.coroutines.flow.Flow

interface MemesRepository {
    fun getAll(): Flow<List<MemeEntity>>
    suspend fun updateFavorite(memeId: Int, isFavorite: Boolean)
    suspend fun insert(memeEntity: MemeEntity)
    suspend fun delete(memeId: Int)
}