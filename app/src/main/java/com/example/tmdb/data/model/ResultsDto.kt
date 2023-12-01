package com.example.tmdb.data.model

import com.squareup.moshi.Json


sealed class ResultsDto {

    data class SeriesResults(
        @Json(name = "results")
        val result: List<SeriesDto>
    ) : ResultsDto()

    data class MovieResults(
        @Json(name = "results")
        val result: List<MovieDto>
    ) : ResultsDto()
}