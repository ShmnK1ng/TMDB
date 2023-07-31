package com.example.tmdb.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction

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
}