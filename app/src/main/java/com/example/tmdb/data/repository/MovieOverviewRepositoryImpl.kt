package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.data.model.utils.TaskManager
import com.example.tmdb.network.MovieOverviewApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieOverviewRepositoryImpl @Inject constructor(
    private val api: MovieOverviewApi,
    private val movieOverviewDao: MovieOverviewDao,
    private val taskManager: TaskManager
) : MovieOverviewRepository {

    override fun getMovieOverview(movieItem: Movie): Flow<MovieOverview> {
        taskManager.startTask { updateMovieOverview(movieItem) }
        return getMovieOverview(movieItem.id)
    }

    private suspend fun getMovieOverviewAsync(movieItem: Movie): Deferred<MovieOverview> = coroutineScope {
        if (movieItem.type == Type.Movies) {
            async { api.getMovieOverview(movieItem.remoteId.toInt()).toMovieOverview(movieItem.id) }
        } else async { api.getSeriesOverview(movieItem.remoteId.toInt()).toMovieOverview(movieItem.id) }
    }

    private suspend fun updateMovieOverview(movieItem: Movie) {
        val movieOverview = getMovieOverviewAsync(movieItem).await()
        movieOverviewDao.updateMovieOverviewDB(movieItem.id, movieOverview)
    }

    private fun getMovieOverview(id: Int): Flow<MovieOverview> {
        return movieOverviewDao.getMovieOverviewWithGenresById(id).toMovieOverviewFlow()
    }
}

interface MovieOverviewRepository {
    fun getMovieOverview(movieItem: Movie): Flow<MovieOverview>
}