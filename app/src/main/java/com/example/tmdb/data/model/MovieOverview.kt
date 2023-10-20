package com.example.tmdb.data.model

data class MovieOverview (
    val id: Int = 0,
    val movieId: String,
    val title: String,
    val overview: String,
    val rating: Double,
    val backdropPath: String,
    val releaseDate: String,
    val genres: List<Genre>
        )