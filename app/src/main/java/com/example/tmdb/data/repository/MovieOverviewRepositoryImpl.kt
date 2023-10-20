package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.network.MovieOverviewApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieOverviewRepositoryImpl @Inject constructor(
    private val api: MovieOverviewApi,
    private val movieOverviewDao: MovieOverviewDao,
    private val coroutineScope: CoroutineScope
) : MovieOverviewRepository {
    private var previousJob: Job? = null

    override fun getMovieOverview(movieItem: Movie): Flow<MovieOverview> {
        if (previousJob?.isActive == false || previousJob == null) {
            previousJob?.cancel()
            previousJob =  coroutineScope.launch { updateMovieOverview(movieItem) }
        }
        return getMovieOverview(movieItem.id)
    }

    private suspend fun getMovieOverviewAsync(movieItem: Movie): Deferred<MovieOverview> = coroutineScope {
        if (movieItem.type == MOVIES_TYPE) {
           async {  api.getMovieOverview(movieItem.remoteId.toInt()).toMovieOverview(movieItem.id) }
        } else async {  api.getSeriesOverview(movieItem.remoteId.toInt()).toMovieOverview(movieItem.id) }
    }

    private suspend fun updateMovieOverview(movieItem: Movie) {
        val movieOverview = getMovieOverviewAsync(movieItem).await()
        movieOverviewDao.updateMovieOverviewDB(movieItem.id, movieOverview)
    }

    private fun getMovieOverview(id: Int): Flow<MovieOverview> {
        return movieOverviewDao.getMovieWithGenresAndOverview(id).map {
            MovieOverview(
                id = it.overview?.id ?: EMPTY,
                movieId = it.overview?.movieId ?: UNKNOWN_MOVIE_ID,
                title = it.overview?.title ?: UNKNOWN_TITTLE,
                overview = it.overview?.overview ?: EMPTY_OVERVIEW,
                rating = it.overview?.rating ?: EMPTY_RATING,
                backdropPath = it.overview?.backdropPath ?: EMPTY_BACKGROUND_PATH,
                releaseDate = it.overview?.releaseDate ?: EMPTY_RELEASE_DATE,
                genres = it.genres?.toGenre() ?: emptyList()
            )
        }
    }
}

interface MovieOverviewRepository {
    fun getMovieOverview(movieItem: Movie): Flow<MovieOverview>
}