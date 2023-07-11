package com.example.tmdb.data.model

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface CategoriesDao {

    @Insert
    suspend fun setCategory(categoryEntity: CategoryEntity)
}