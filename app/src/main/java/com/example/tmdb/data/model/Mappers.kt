package com.example.tmdb.data.model

fun List<MovieDto>.moviesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            remoteId = it.remoteId,
            title = it.title,
            rating = it.rating,
            posterPath = IMAGE_BASE_PATH + it.posterPath,
            type = it.type
        )
    }
}

fun List<SeriesDto>.seriesDtoListToMovieList(): List<Movie> {
    return this.map {
        Movie(
            remoteId = it.remoteId,
            title = it.name,
            rating = it.rating,
            posterPath = IMAGE_BASE_PATH + it.posterPath,
            type = it.type
        )
    }
}

fun Category.toMovieEntityList(): List<MovieEntity>? {
    return this.movieList?.map { movie ->
        MovieEntity(
            id = movie.id,
            remoteId = movie.remoteId,
            title = movie.title,
            rating = movie.rating,
            posterPath = movie.posterPath,
            type = movie.type
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

fun List<MovieEntity>.toMovieList(): List<Movie> {
    return this.map { movieEntity ->
        Movie(
            id = movieEntity.id,
            remoteId = movieEntity.remoteId,
            title = movieEntity.title,
            rating = movieEntity.rating,
            posterPath = movieEntity.posterPath,
            type = movieEntity.type
        )
    }
}

fun CategoryEntity.toCategoryName(): CategoryName {
    return CategoryName(
        name = this.categoryName,
        id = this.id
    )
}

fun MovieOverviewDto.toMovieOverview(id: Int): MovieOverview {
    return MovieOverview(
        id = id,
        movieId = this.remoteId,
        title = this.title,
        overview = this.overview,
        rating = this.rating,
        backdropPath = POSTER_BASE_PATH + this.backdropPath,
        releaseDate = this.releaseDate,
        genres = this.genres
    )
}

fun SeriesOverviewDto.toMovieOverview(id: Int): MovieOverview {
    return MovieOverview(
        id = id,
        movieId = this.remoteId,
        title = this.title,
        overview = this.overview,
        rating = this.rating,
        backdropPath = POSTER_BASE_PATH + this.backdropPath,
        releaseDate = this.releaseDate,
        genres = this.genres
    )
}

fun List<GenreEntity>.toGenre(): List<Genre> {
    return this.map {
        Genre(
            name = it.name
        )
    }
}

fun MovieOverview.toMovieOverviewEntity(): MovieOverviewEntity {
    return MovieOverviewEntity(
        id = 0,
        movieId = this.id.toString(),
        title = title,
        overview = overview,
        rating = rating,
        backdropPath = backdropPath,
        releaseDate = releaseDate
    )
}

fun MovieOverview.toMovieGenresEntity(): List<GenreEntity> {
    return this.genres.map {
        GenreEntity(
            id = 0,
            movieId = this.id,
            name = it.name
        )
    }
}