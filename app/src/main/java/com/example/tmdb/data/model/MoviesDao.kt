package com.example.tmdb.data.model

import androidx.room.*

@Dao
abstract class MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveCategoryAndMovieDependency(categoryAndMoviesDependenciesEntity: CategoryAndMoviesDependenciesEntity)

    @Transaction
    open suspend fun saveMoviesAndDependencies(categoriesList: List<Category>?) {
        categoriesList?.forEach { category ->
            val categoryId = category.categoryName.id
            deleteMoviesByCategoryId(categoryId)
            category.toMovieEntityList()?.forEach { movieEntity ->
                saveMovie(movieEntity)
                saveCategoryAndMovieDependency(
                    CategoryAndMoviesDependenciesEntity(
                        categoryId,
                        movieEntity.id
                    )
                )
            }
        }
    }

    @Query("DELETE FROM movies WHERE id IN (SELECT movie_id FROM categories_movies WHERE category_id = :categoryId)")
    abstract suspend fun deleteMoviesByCategoryId(categoryId: Int)
}