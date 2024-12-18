package com.icdominguez.icdominguez.memecreator

import android.app.Application
import androidx.room.Room
import com.icdominguez.icdominguez.memecreator.data.database.MemeDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MemeCreatorApp : Application() /*{
    companion object {
        lateinit var memeDatabase: MemeDatabase
    }
    override fun onCreate() {
        super.onCreate()
        memeDatabase = Room.databaseBuilder(applicationContext, MemeDatabase::class.java, "memes_database").build()
    }
}*/