package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "genres",
    foreignKeys = [
        ForeignKey(
            entity = MovieOverviewEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieOverviewId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieOverviewId: Int,
    val name: String
)

data class MovieOverviewWithGenres(
    @Embedded
    val movieOverview: MovieOverviewEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieOverviewId"
    )
    val genres: List<GenreEntity>
)