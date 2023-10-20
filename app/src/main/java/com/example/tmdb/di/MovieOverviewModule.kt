package com.example.tmdb.di

import com.example.tmdb.data.repository.MovieOverviewRepository
import com.example.tmdb.data.repository.MovieOverviewRepositoryImpl
import com.example.tmdb.data.usecase.GetMovieOverviewUseCase
import com.example.tmdb.data.usecase.GetMovieOverviewUseCaseImpl
import com.example.tmdb.network.MovieOverviewApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieOverviewModule {

    @Binds
    @Singleton
    abstract fun bindMovieOverviewRepository(repository: MovieOverviewRepositoryImpl): MovieOverviewRepository

    @Binds
    abstract fun bindGetCategoriesUseCase(movieOverviewSource: GetMovieOverviewUseCaseImpl): GetMovieOverviewUseCase
}

@Module
@InstallIn(SingletonComponent::class)
class MovieOverviewApiModule {

    @Provides
    @Singleton
    fun provideMovieOverviewApi(retrofit: Retrofit): MovieOverviewApi =
        retrofit.create(MovieOverviewApi::class.java)
}