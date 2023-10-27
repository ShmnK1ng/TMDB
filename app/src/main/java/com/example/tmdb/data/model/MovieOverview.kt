package com.example.tmdb.data.model

import java.time.Instant

data class MovieOverview(
    val id: Int,
    val movieId: String,
    val title: String,
    val overview: String,
    val rating: Double,
    val backdropPath: String,
    val releaseDate: Instant,
    val genres: List<Genre>
)