package com.example.tmdb.di

import com.example.tmdb.data.repository.CategoriesRepository
import com.example.tmdb.data.repository.CategoriesRepositoryImpl
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import com.example.tmdb.data.usecase.GetCategoriesUseCaseImpl
import com.example.tmdb.network.CategoriesApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoriesModule {

    @Binds
    @Singleton
    abstract fun provideCategoryRepository(repository: CategoriesRepositoryImpl) : CategoriesRepository

    @Binds
    @Singleton
    abstract fun provideMovieListSource(movieListSource: GetCategoriesUseCaseImpl) : GetCategoriesUseCase
}

@Module
@InstallIn(SingletonComponent::class)
class CategoriesApiModule {

    @Provides
    @Singleton
    internal fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)
}