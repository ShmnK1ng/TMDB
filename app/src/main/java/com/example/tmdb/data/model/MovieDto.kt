package com.example.tmdb.data.model

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id")
    val remoteId: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
)