package com.icdominguez.icdominguez.memecreator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.icdominguez.icdominguez.memecreator.domain.model.MemeEntity

@Database(entities = [MemeEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MemeDatabase: RoomDatabase() {
    abstract fun memeDao(): MemeDao
}