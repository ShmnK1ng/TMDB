package com.example.tmdb.ui.home

import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.usecase.FakeGetCategoriesUseCase
import com.example.tmdb.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: HomeFragmentViewModel

    @Before
    fun setupViewModel() {
        viewModel = HomeFragmentViewModel(FakeGetCategoriesUseCase())
    }

    @Test
    fun `should open MovieOverview in case of list item click`() = runTest {
        val result = mutableListOf<Movie?>()
        val movie = Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)
        viewModel.onMovieItemClicked(movie)
        viewModel.goToMovieOverview.first {
            result.add(it)
        }
        assertEquals(listOf(movie), result)
    }

    @Test
    fun `should show error in case of an error`() = runTest {
        val result = mutableListOf<Result.Failure?>()
        viewModel.showError.first {
            result.add(it)
        }
        assertEquals(listOf(Result.Failure.NetworkError), result)
    }

    @Test
    fun `should show list of categories in case of receiving it`() = runTest {
        val result = mutableListOf<List<Category>>()
        val listCategories = listOf(listOf(Category(CategoryName(1, 1), listOf(Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)))))
        viewModel.categories.first {
            result.add(it)
        }
        assertEquals(listCategories, result)
    }
}