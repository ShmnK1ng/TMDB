package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "movies"
)

data class MovieEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "movie_id")
    val movieId: String,
    val title: String,
    val rating: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String
)