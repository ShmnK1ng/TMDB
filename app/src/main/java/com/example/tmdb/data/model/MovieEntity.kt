package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "movies"
)

data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val remoteId: String,
    val title: String,
    val rating: Double,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val type: Int
)