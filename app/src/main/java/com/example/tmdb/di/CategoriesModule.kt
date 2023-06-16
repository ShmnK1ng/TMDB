package com.example.tmdb.di

import com.example.tmdb.data.repository.CategoriesRepository
import com.example.tmdb.data.repository.CategoriesRepositoryImpl
import com.example.tmdb.data.usecase.MovieListSource
import com.example.tmdb.data.usecase.MovieListSourceImpl
import com.example.tmdb.network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CategoriesModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(movieApi: MovieApi) : CategoriesRepository {
        return CategoriesRepositoryImpl(movieApi)
    }

    @Provides
    @Singleton
    fun provideMovieListSource(categoriesRepository: CategoriesRepository) : MovieListSource {
        return MovieListSourceImpl(categoriesRepository)
    }
}