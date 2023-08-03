package com.example.tmdb.data.model

import androidx.room.*

@Dao
abstract class MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveMovie(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveCategoryAndMovieDependency(categoryAndMoviesDependenciesEntity: CategoryAndMoviesDependenciesEntity)

    @Transaction
    open suspend fun saveMoviesAndDependencies(moviesEntityList: List<MovieEntity>?, categoryId: Int) {
        moviesEntityList?.forEach { movieEntity ->
            saveMovie(movieEntity)
            saveCategoryAndMovieDependency(CategoryAndMoviesDependenciesEntity(categoryId, movieEntity.id))
        }
    }

    @Query("DELETE FROM movies")
    abstract suspend fun deleteOldMovies()
}