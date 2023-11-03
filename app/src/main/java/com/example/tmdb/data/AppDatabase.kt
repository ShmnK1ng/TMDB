package com.example.tmdb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.data.model.*
import com.example.tmdb.data.model.utils.InstantConverter
import com.example.tmdb.data.model.utils.TypeConverter

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
@TypeConverters(
    TypeConverter::class,
    InstantConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao
    abstract fun moviesDao(): MoviesDao
    abstract fun movieOverviewDao(): MovieOverviewDao
}