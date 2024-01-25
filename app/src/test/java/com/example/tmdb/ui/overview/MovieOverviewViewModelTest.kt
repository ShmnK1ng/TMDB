package com.example.tmdb.ui.overview

import androidx.lifecycle.viewModelScope
import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.collectForTest
import com.example.tmdb.data.model.Genre
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.usecase.GetMovieOverviewUseCaseStub
import com.example.tmdb.ui.home.MovieOverviewViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant

@ExperimentalCoroutinesApi
class MovieOverviewViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieOverviewViewModel
    private val expectedResult = MovieOverview(0, "TestId", "TestTittle", "TestOverview", 0.0, "Test", Instant.ofEpochSecond(100), listOf(Genre("Test")))


    @Before
    fun setupViewModel() {
        val movie = Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)
        viewModel = MovieOverviewViewModel(GetMovieOverviewUseCaseStub(expectedResult), movie)
    }

    @Test
    fun `should return movie overview`() {
        val actualResult = viewModel.movieOverview.collectForTest(viewModel.viewModelScope)
        assertEquals(listOf(expectedResult), actualResult)
    }
}