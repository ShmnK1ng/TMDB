package com.example.tmdb.data.model

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

fun Category.toMovieEntityList(): List<MovieEntity>? {
    return this.movieList?.map { movie ->
        MovieEntity(
            id = movie.id,
            title = movie.title,
            rating = movie.rating,
            posterPath = movie.posterPath
        )
    }
}

fun List<CategoryEntity>.toCategoryNamesList(): List<CategoryName> {
    return this.map {
        CategoryName(
            name = it.categoryName,
            id = it.id,
        )
    }
}