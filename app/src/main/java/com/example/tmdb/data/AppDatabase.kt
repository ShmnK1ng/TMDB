package com.example.tmdb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.model.*

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        MovieEntity::class,
        CategoryAndMoviesDependenciesEntity::class,
        MovieOverviewEntity::class,
        GenreEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao
    abstract fun moviesDao(): MoviesDao
    abstract fun movieOverviewDao(): MovieOverviewDao
}