package com.example.tmdb.data.model

import com.squareup.moshi.Json

data class MovieDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "vote_average")
    val rating: Double,
    @Json(name = "poster_path")
    val posterPath: String
)

data class MovieResultsDto(
    @Json(name = "results")
    val result: List<MovieDto>
) {
    fun toMovieList(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        for (movieDto in result) {
            val movie = Movie(
                id = movieDto.id,
                title = movieDto.title,
                rating = movieDto.rating,
                posterPath = movieDto.posterPath
            )
            movieList.add(movie)
        }
        return movieList.toList()
    }
}