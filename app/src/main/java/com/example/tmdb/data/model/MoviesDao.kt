package com.example.tmdb.data.model

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface MoviesDao {

    @Insert
    suspend fun saveMovie(movieEntity: MovieEntity)
}