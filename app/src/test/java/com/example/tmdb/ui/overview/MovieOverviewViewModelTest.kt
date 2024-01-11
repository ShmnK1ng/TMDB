package com.example.tmdb.ui.overview

import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.collectInViewModelScope
import com.example.tmdb.data.model.Genre
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.usecase.FakeGetMovieOverviewUseCase
import com.example.tmdb.ui.home.MovieOverviewViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant

@ExperimentalCoroutinesApi
class MovieOverviewViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieOverviewViewModel

    @Before
    fun setupViewModel() {
        val movie = Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)
        viewModel = MovieOverviewViewModel(FakeGetMovieOverviewUseCase(), movie)
    }

    @Test
    fun `should show movie overview in case of receiving it`() {
        val result = viewModel.movieOverview.collectInViewModelScope(viewModel)
        Assert.assertEquals(listOf(MovieOverview(0, "TestId", "TestTittle", "TestOverview", 0.0, "Test", Instant.ofEpochSecond(100), listOf(Genre("Test"))  )), result)
    }
}