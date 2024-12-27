package com.icdominguez.icdominguez.master_meme.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "memes")
data class MemeEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val path: String?,
    val name: String?,
    val date: LocalDateTime = LocalDateTime.now(),
    val favorite: Boolean = false,
)