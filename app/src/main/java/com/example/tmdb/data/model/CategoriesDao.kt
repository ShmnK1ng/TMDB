package com.example.tmdb.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCategory(categoryEntity: CategoryEntity)
}