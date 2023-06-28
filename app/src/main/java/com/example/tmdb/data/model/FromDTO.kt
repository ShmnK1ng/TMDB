package com.example.tmdb.data.model

fun List<MovieDto>.moviesDtoListToMovieList(): List<Movie> {
    val movieList: MutableList<Movie> = mutableListOf()
    this.map {
        movieList.add(
            Movie(
                id = it.id, title = it.title, rating = it.rating, posterPath = it.posterPath
            )
        )
    }
    return movieList.toList()
}

fun List<SeriesDto>.seriesDtoListToMovieList(): List<Movie> {
    val movieList: MutableList<Movie> = mutableListOf()
    this.map {
        movieList.add(
            Movie(
                id = it.id, title = it.name, rating = it.rating, posterPath = it.posterPath
            )
        )
    }
    return movieList.toList()
}