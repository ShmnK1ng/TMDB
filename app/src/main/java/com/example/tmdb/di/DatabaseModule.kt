package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.data.AppDatabase
import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.DATABASE_NAME
import com.example.tmdb.data.model.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context, AppDatabase::class.java, DATABASE_NAME
        )
            .createFromAsset("init_database.db")
            .build()

    @Provides
    fun provideCategoriesDao(appDatabase: AppDatabase): CategoriesDao {
        return appDatabase.categoriesDao()
    }

    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MoviesDao {
        return appDatabase.moviesDao()
    }
}