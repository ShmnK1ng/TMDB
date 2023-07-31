package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "movies"
)

data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val rating: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String
)