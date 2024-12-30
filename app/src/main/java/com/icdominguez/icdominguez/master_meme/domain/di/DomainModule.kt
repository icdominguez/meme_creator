package com.icdominguez.icdominguez.master_meme.domain.di

import com.icdominguez.icdominguez.master_meme.data.repository.FileManagerRepositoryImpl
import com.icdominguez.icdominguez.master_meme.data.repository.MemeTemplatesRepositoryImpl
import com.icdominguez.icdominguez.master_meme.data.repository.MemesRepositoryImpl
import com.icdominguez.icdominguez.master_meme.domain.repository.FileManagerRepository
import com.icdominguez.icdominguez.master_meme.domain.repository.MemeTemplatesRepository
import com.icdominguez.icdominguez.master_meme.domain.repository.MemesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun provideMemesRepository(memesRepositoryImpl: MemesRepositoryImpl): MemesRepository

    @Binds
    fun bindMemeTemplatesRepository(memeTemplatesRepositoryImpl: MemeTemplatesRepositoryImpl): MemeTemplatesRepository

    @Binds
    fun bindFileManagerRepository(fileManagerRepositoryImpl: FileManagerRepositoryImpl): FileManagerRepository
}