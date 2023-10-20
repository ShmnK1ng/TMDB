package com.example.tmdb.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieOverviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMovieOverview(movieOverviewEntity: MovieOverviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveMovieGenres(movieGenresList: List<GenreEntity>)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    abstract fun getMovieWithGenresAndOverview(movieId: Int): Flow<MovieWithGenresAndOverview>

    @Query("SELECT id FROM movies_overview WHERE movieId = :movieId")
    abstract fun getMovieOverviewIdByMovieId(movieId: Int): Int?

    @Update
    abstract fun updateMovieOverview(movieOverview: MovieOverviewEntity)

    @Query("DELETE FROM genres WHERE movieId = :movieId")
    abstract fun deleteGenresForMovie(movieId: Int)

    @Transaction
    open suspend fun updateMovieOverviewDB(movieId: Int, movieOverview: MovieOverview) {
        val movieOverviewEntity = movieOverview.toMovieOverviewEntity()
        val genreEntityList = movieOverview.toMovieGenresEntity()
        val movieOverviewId = getMovieOverviewIdByMovieId(movieId)
        if (movieOverviewId == null) {
            saveMovieOverview(movieOverviewEntity)
            saveMovieGenres(genreEntityList)
        } else {
            movieOverviewEntity.id = movieOverviewId
            updateMovieOverview(movieOverviewEntity)
            deleteGenresForMovie(movieId)
            saveMovieGenres(genreEntityList)
        }
    }
}