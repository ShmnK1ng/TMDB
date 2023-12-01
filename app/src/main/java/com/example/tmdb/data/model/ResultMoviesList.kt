package com.example.tmdb.data.model

import com.example.tmdb.network.Result

data class ResultMoviesList(
    val moviesList: List<Movie>?,
    val error: Result.Failure<*>?
)