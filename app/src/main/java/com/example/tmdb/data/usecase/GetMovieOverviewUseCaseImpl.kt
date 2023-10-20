package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import com.example.tmdb.data.repository.MovieOverviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieOverviewUseCaseImpl @Inject constructor(private val repository: MovieOverviewRepository): GetMovieOverviewUseCase {
    override fun getMovieOverview(movieItem: Movie): Flow<MovieOverview> {
       return repository.getMovieOverview(movieItem)
    }

}

interface GetMovieOverviewUseCase {
    fun getMovieOverview(movieItem: Movie): Flow<MovieOverview>
}