package com.example.tmdb.di

import com.example.tmdb.data.DataRepository
import com.example.tmdb.data.DataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindDataRepository(dataRepository: DataRepositoryImpl) : DataRepository
}