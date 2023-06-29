package com.example.tmdb.data.model

fun List<MovieDto>.moviesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            id = it.id, title = it.title, rating = it.rating, posterPath = it.posterPath
        )
    }
}

fun List<SeriesDto>.seriesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            id = it.id, title = it.name, rating = it.rating, posterPath = it.posterPath
        )
    }
}