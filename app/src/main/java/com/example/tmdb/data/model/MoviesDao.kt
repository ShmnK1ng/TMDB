package com.example.tmdb.data.model

import androidx.room.*

@Dao
abstract class MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveMovie(movieEntity: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveCategoryAndMovieDependency(categoryAndMoviesDependenciesEntity: CategoryAndMoviesDependenciesEntity)

    @Query("SELECT id FROM movies WHERE remoteId = :remoteId AND type = :type")
    abstract fun getMovieByRemoteIdAndType(remoteId: String, type: Int): Int?

    @Query("UPDATE movies SET title = :newTitle, rating = :newRating, poster_path = :newPosterPath WHERE remoteId = :remoteId AND type = :type")
    abstract fun updateMovieByRemoteIdAndType(remoteId: String, type: Int, newTitle: String, newRating: Double, newPosterPath: String)

    @Transaction
    open suspend fun saveMoviesAndDependencies(categoriesList: List<Category>?) {
        categoriesList?.forEach { category ->
            val categoryId = category.categoryName.id
            deleteMoviesByCategoryId(categoryId)
            category.toMovieEntityList()?.forEach { movieEntity ->
                var movieId = getMovieByRemoteIdAndType(movieEntity.remoteId, movieEntity.type)
                if (movieId == null) {
                   movieId = saveMovie(movieEntity).toInt()
                } else {
                    updateMovieByRemoteIdAndType(movieEntity.remoteId, movieEntity.type, movieEntity.title, movieEntity.rating, movieEntity.posterPath)
                }
                saveCategoryAndMovieDependency(
                    CategoryAndMoviesDependenciesEntity(
                        categoryId,
                        movieId
                    )
                )
            }
        }
    }

    @Query("DELETE FROM movies WHERE id IN (SELECT movie_id FROM categories_movies WHERE category_id = :categoryId)")
    abstract suspend fun deleteMoviesByCategoryId(categoryId: Int)
}