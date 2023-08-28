package com.example.tmdb.di

import com.example.tmdb.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesScopesModule {

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return App.applicationScope
    }
}