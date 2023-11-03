package com.example.tmdb.data.model

import androidx.room.*
import java.time.Instant

@Entity(
    tableName = "movies_overview",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class MovieOverviewEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: String,
    val title: String,
    val overview: String,
    val rating: Double,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: Instant
)