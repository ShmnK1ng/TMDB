package com.example.tmdb.data.model

import com.squareup.moshi.Json

class SeriesDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "poster_path")
    val posterPath: String
)

data class SeriesResultsDto(
    @Json(name = "results")
    val result: List<SeriesDto>
)