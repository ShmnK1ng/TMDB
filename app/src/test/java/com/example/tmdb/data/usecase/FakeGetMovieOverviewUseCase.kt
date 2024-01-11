package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Genre
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.Instant

class FakeGetMovieOverviewUseCase : GetMovieOverviewUseCase {
    override fun getMovieOverview(movieItem: Movie): Flow<MovieOverview> {
        return MutableStateFlow(
            MovieOverview(0, "TestId", "TestTittle", "TestOverview", 0.0, "Test", Instant.ofEpochSecond(100), listOf(Genre("Test"))  )
        )
    }
}