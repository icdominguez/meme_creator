package com.icdominguez.icdominguez.master_meme.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icdominguez.icdominguez.master_meme.domain.model.MemeEntity

@Database(entities = [MemeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MemeDatabase: RoomDatabase() {
    abstract fun memeDao(): MemeDao
}