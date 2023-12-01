package com.example.tmdb.data.model

import com.squareup.moshi.Json

data class SeriesDto(
    @Json(name = "id")
    val remoteId: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "poster_path")
    val posterPath: String?,
)