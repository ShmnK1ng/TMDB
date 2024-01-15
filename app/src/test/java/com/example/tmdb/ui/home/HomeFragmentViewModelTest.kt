package com.example.tmdb.ui.home

import androidx.lifecycle.viewModelScope
import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.collectForTest
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.usecase.GetCategoriesUseCaseStub
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

    private val getCategoriesUseCaseStub = GetCategoriesUseCaseStub()

    @Before
    fun setupViewModel() {
        viewModel = HomeFragmentViewModel(getCategoriesUseCaseStub)
    }

    @Test
    fun `should open MovieOverview in case of list item click`() {
        val movie1 = Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)
        val movie2 = Movie(0, "remoteId", "Title2", 0.0, "posterPath", Type.Series)
        val result = viewModel.goToMovieOverview.collectForTest(viewModel.viewModelScope) {
            viewModel.onMovieItemClicked(movie1)
            viewModel.onMovieItemClicked(movie2)
            viewModel.resetClickState()
        }
        assertEquals(listOf(null, movie1, movie2, null), result)
    }

    @Test
    fun `should return network error and empty list of categories`() {
        getCategoriesUseCaseStub.setResult(ResultCategories(emptyList(), Result.Failure.NetworkError))
        val actualResult = viewModel.showError.collectForTest(viewModel.viewModelScope) {
            viewModel.resetErrorState()
        }
        assertEquals(listOf(Result.Failure.NetworkError, null), actualResult)
        val actualCategoriesList = viewModel.categories.collectForTest(viewModel.viewModelScope)
        assertEquals(listOf(emptyList<Category>()), actualCategoriesList)
    }

    @Test
    fun `should return other error and empty list of categories`() {
        getCategoriesUseCaseStub.setResult(ResultCategories(emptyList(), Result.Failure.OtherError))
        val actualResult = viewModel.showError.collectForTest(viewModel.viewModelScope) {
            viewModel.resetErrorState()
        }
        assertEquals(listOf(Result.Failure.OtherError, null), actualResult)
        val actualCategoriesList = viewModel.categories.collectForTest(viewModel.viewModelScope)
        assertEquals(listOf(emptyList<Category>()), actualCategoriesList)
    }

    @Test
    fun `should return list of categories and null from errors`() {
        val result = ResultCategories(
            listOf(
                Category(CategoryName(1, 1), listOf(Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)))
            ), null
        )
        getCategoriesUseCaseStub.setResult(result)
        val expectedResult = listOf(result.listCategories)
        val actualResult = viewModel.categories.collectForTest(viewModel.viewModelScope)
        assertEquals(expectedResult, actualResult)
        val actualErrorResult = viewModel.showError.collectForTest(viewModel.viewModelScope)
        assertEquals(listOf(null), actualErrorResult)
    }

    @Test
    fun `should return list of categories and network error`() {
        val result = ResultCategories(
            listOf(
                Category(CategoryName(1, 1), listOf(Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)))
            ), Result.Failure.NetworkError
        )
        getCategoriesUseCaseStub.setResult(result)
        val expectedResult = listOf(result.listCategories)
        val actualResult = viewModel.categories.collectForTest(viewModel.viewModelScope)
        assertEquals(expectedResult, actualResult)
        val actualErrorResult = viewModel.showError.collectForTest(viewModel.viewModelScope)
        assertEquals(listOf(Result.Failure.NetworkError), actualErrorResult)
    }
}