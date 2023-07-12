package com.example.tmdb.di

import com.example.tmdb.data.repository.CategoriesRepository
import com.example.tmdb.data.repository.CategoriesRepositoryImpl
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import com.example.tmdb.data.usecase.GetCategoriesUseCaseImpl
import com.example.tmdb.data.usecase.SetCategoriesUseCase
import com.example.tmdb.data.usecase.SetCategoriesUseCaseImpl
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
    abstract fun bindCategoryRepository(repository: CategoriesRepositoryImpl): CategoriesRepository

    @Binds
    abstract fun bindGetCategoriesUseCase(movieListSource: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    abstract fun bindSetCategoriesUseCase(movieListSource: SetCategoriesUseCaseImpl): SetCategoriesUseCase
}

@Module
@InstallIn(SingletonComponent::class)
class CategoriesApiModule {

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi =
        retrofit.create(CategoriesApi::class.java)
}