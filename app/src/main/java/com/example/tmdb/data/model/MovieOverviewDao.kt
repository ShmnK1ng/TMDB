package com.example.tmdb.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieOverviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMovieOverview(movieOverviewEntity: MovieOverviewEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMovieGenres(movieGenresList: List<GenreEntity>)

    @Transaction
    @Query("SELECT * FROM movies_overview WHERE movieId = :movieId")
    abstract fun getMovieOverviewWithGenresById(movieId: Int): Flow<MovieOverviewWithGenres>

    @Query("DELETE FROM movies_overview WHERE movieId = :movieId")
    abstract fun deleteMovieOverviewById(movieId: Int)

    @Update
    abstract fun updateMovieOverview(movieOverview: MovieOverviewEntity)

    @Transaction
    open suspend fun updateMovieOverviewDB(movieId: Int, movieOverview: MovieOverview) {
        deleteMovieOverviewById(movieId)
        val movieOverviewId = saveMovieOverview(movieOverview.toMovieOverviewEntity()).toInt()
        saveMovieGenres(movieOverview.toMovieGenresEntity(movieOverviewId))
    }
}