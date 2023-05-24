package com.example.tmdb.di

import com.example.tmdb.data.DataRepository
import com.example.tmdb.data.DataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    @Provides
    fun provideDataRepository() : DataRepository {
        return DataRepositoryImpl()
    }
}