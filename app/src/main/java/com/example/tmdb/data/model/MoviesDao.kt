package com.example.tmdb.data.model

import androidx.room.Dao

@Dao
interface MoviesDao {

    suspend fun setMovie(movieEntity: MovieEntity)
}