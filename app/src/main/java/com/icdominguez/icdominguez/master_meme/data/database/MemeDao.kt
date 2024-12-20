package com.icdominguez.icdominguez.master_meme.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.icdominguez.icdominguez.master_meme.domain.model.MemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {
    @Query("SELECT * FROM memes")
    fun getAll(): Flow<List<MemeEntity>>

    @Query("UPDATE memes SET favorite = :favorite  WHERE uid = :uid")
    suspend fun updateFavorite(uid: Int, favorite: Boolean)

    @Insert
    suspend fun insertNewMeme(vararg memeEntity: MemeEntity)

    @Query("DELETE FROM memes WHERE uid = :uid")
    suspend fun delete(uid: Int)
}