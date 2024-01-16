package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetMovieOverviewUseCaseStub(private val result: MovieOverview) : GetMovieOverviewUseCase {
    override fun getMovieOverview(movieItem: Movie): Flow<MovieOverview> {
        return flowOf(result)
    }
}