package com.example.tmdb.data.model

import androidx.room.*

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories")
    suspend fun getAllCategoryNames(): List<CategoryEntity>
}