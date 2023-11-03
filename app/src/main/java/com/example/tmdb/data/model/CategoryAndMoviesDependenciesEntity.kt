package com.example.tmdb.data.model

import androidx.room.*

@Entity(
    tableName = "categories_movies",
    primaryKeys = ["category_id", "movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CategoryAndMoviesDependenciesEntity(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "movie_id", index = true)
    val movieId: Int
)

data class CategoryAndMovies(
    @Embedded
    val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CategoryAndMoviesDependenciesEntity::class,
            parentColumn = "category_id",
            entityColumn = "movie_id"
        )
    )
    val moviesEntity: List<MovieEntity>
)