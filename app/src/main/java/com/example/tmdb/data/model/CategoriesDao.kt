package com.example.tmdb.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories")
    suspend fun getAllCategoryNames(): List<CategoryEntity>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesAndMovies(): Flow<List<CategoryAndMovies>>
}