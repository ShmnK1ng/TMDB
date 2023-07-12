package com.example.tmdb.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.CategoryEntity
import com.example.tmdb.data.model.MovieEntity
import com.example.tmdb.data.model.MoviesDao

@Database(
    version = 1,
    entities = [
        CategoryEntity::class,
        MovieEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoriesDao(): CategoriesDao
    abstract fun moviesDao(): MoviesDao
}