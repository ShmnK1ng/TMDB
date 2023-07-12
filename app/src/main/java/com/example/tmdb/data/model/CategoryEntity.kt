package com.example.tmdb.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    indices = [
        Index("category_name", unique = true)
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "category_name") val categoryName: String,
) {
}