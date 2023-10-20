package com.example.tmdb.data.model

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "genres",
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = CASCADE
        )
    ]
)
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val movieId: Int,
    val name: String
)

data class MovieWithGenresAndOverview(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entity = MovieOverviewEntity::class,
        entityColumn = "movieId"
    )
    val overview: MovieOverviewEntity?,
    @Relation(
        parentColumn = "id",
        entity = GenreEntity::class,
        entityColumn = "movieId"
    )
    val genres: List<GenreEntity>?
)