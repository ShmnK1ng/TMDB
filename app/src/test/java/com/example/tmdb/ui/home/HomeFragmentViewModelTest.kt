package com.example.tmdb.ui.home

import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.collectInViewModelScope
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.usecase.FakeGetCategoriesUseCase
import com.example.tmdb.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    fun `should open MovieOverview in case of list item click`() {
        val movie1 = Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)
        val movie2 = Movie(0, "remoteId", "Title2", 0.0, "posterPath", Type.Series)
        val result = viewModel.goToMovieOverview.collectInViewModelScope(viewModel) {
            viewModel.onMovieItemClicked(movie1)
            viewModel.onMovieItemClicked(movie2)
            viewModel.resetClickState()
        }
        assertEquals(listOf(null, movie1, movie2, null), result)
    }

    @Test
    fun `should show error in case of an error`() {
        val result = viewModel.showError.collectInViewModelScope(viewModel) {
            viewModel.resetErrorState()
        }
        assertEquals(listOf(Result.Failure.NetworkError, null), result)
    }

    @Test
    fun `should show list of categories in case of receiving it`() {
        val listCategories = listOf(listOf(Category(CategoryName(1, 1), listOf(Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)))))
        val result = viewModel.categories.collectInViewModelScope(viewModel)
        assertEquals(listCategories, result)
    }
}