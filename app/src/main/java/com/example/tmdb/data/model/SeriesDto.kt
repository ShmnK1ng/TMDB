package com.example.tmdb.data.model

import com.squareup.moshi.Json

class SeriesDto (
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "poster_path")
    val posterPath: String
)

data class SeriesResultsDto(@Json(name = "results")
                           val result : List<SeriesDto>) {
    fun toMovieList(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        for (seriesDto in result) {
            val movie = Movie(
                id = seriesDto.id,
                title = seriesDto.name,
                rating = seriesDto.rating,
                posterPath = seriesDto.posterPath
            )
            movieList.add(movie)
        }
        return movieList.toList()
    }
}