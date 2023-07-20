package com.example.tmdb.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
)
data class CategoryEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_name")
    val categoryName: Int,
)