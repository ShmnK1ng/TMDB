package com.example.tmdb.data.model

import java.util.UUID

fun List<MovieDto>.moviesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.title,
            rating = it.rating,
            posterPath = IMAGE_BASE_PATH + it.posterPath
        )
    }
}

fun List<SeriesDto>.seriesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            id = it.id,
            title = it.name,
            rating = it.rating,
            posterPath = IMAGE_BASE_PATH + it.posterPath
        )
    }
}

fun Category.toMovieEntityList(): List<MovieEntity> {
    return this.movieList.map { movie ->
        MovieEntity(
            id = UUID.randomUUID().toString(),
            movieId = movie.id,
            title = movie.title,
            rating = movie.rating,
            posterPath = movie.posterPath
        )
    }
}