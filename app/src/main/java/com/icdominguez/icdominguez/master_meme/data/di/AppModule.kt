package com.icdominguez.icdominguez.master_meme.data.di

import android.content.Context
import androidx.room.Room
import com.icdominguez.icdominguez.master_meme.data.database.MemeDao
import com.icdominguez.icdominguez.master_meme.data.database.MemeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MemeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MemeDatabase::class.java,
            "memes"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMemeDao(memeDatabase: MemeDatabase): MemeDao {
        return memeDatabase.memeDao()
    }
}