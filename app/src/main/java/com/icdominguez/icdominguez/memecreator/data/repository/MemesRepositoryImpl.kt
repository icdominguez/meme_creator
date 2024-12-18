package com.icdominguez.icdominguez.memecreator.data.repository

import com.icdominguez.icdominguez.memecreator.data.database.MemeDao
import com.icdominguez.icdominguez.memecreator.domain.model.MemeEntity
import com.icdominguez.icdominguez.memecreator.domain.repository.MemesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MemesRepositoryImpl @Inject constructor(
    private val memeDao: MemeDao
): MemesRepository {
    override fun getAll(): Flow<List<MemeEntity>> = flow {
        memeDao.getAll().collect { memes ->
            emit(memes)
        }
    }

    override suspend fun updateFavorite(memeId: Int, isFavorite: Boolean) {
        return withContext(Dispatchers.IO) {
            memeDao.updateFavorite(memeId, isFavorite)
        }
    }

    override suspend fun insert(memeEntity: MemeEntity) {
        return withContext(Dispatchers.IO) {
            memeDao.insertNewMeme(memeEntity)
        }
    }

    override suspend fun delete(memeId: Int) {
        return withContext(Dispatchers.IO) {
            memeDao.delete(memeId)
        }
    }
}