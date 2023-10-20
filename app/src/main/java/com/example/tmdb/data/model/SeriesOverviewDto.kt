package com.example.tmdb.data.model

import com.squareup.moshi.Json

data class SeriesOverviewDto(
    @Json(name = "id")
    val remoteId: String,
    @Json(name = "name")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "first_air_date")
    val releaseDate: String,
    @Json(name = "genres")
    val genres: List<Genre>
)