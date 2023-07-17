package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "movies",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)

data class MovieEntity(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @PrimaryKey
    val id: String,
    val title: String,
    val rating: Double,
    @ColumnInfo(name = "poster_key")
    val posterPath: String
)