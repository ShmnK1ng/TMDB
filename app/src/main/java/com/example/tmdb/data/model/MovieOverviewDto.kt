package com.example.tmdb.data.model

import com.squareup.moshi.Json

data class MovieOverviewDto (
    @Json(name = "id")
    val remoteId: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "backdrop_path")
    val backdropPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "genres")
    val genres: List<Genre>
        )