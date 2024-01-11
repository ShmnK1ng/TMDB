package com.example.tmdb.data.repository

import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.FakeCategoriesDao
import com.example.tmdb.data.model.FakeMoviesDao
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieDto
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.ResultsDto
import com.example.tmdb.data.model.SeriesDto
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.model.utils.TaskManager
import com.example.tmdb.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: CategoriesRepositoryImpl
    private val result1: Result<ResultsDto.MovieResults> = Result.Success(ResultsDto.MovieResults(listOf(MovieDto("Test1", "Test1", 0.0, "Test1"))))
    private val result2: Result<ResultsDto.SeriesResults> = Result.Success(ResultsDto.SeriesResults(listOf(SeriesDto("Test2", "Test2", 0.0, "Test2"))))
    private val result3: Result<ResultsDto.MovieResults> = Result.Success(ResultsDto.MovieResults(listOf(MovieDto("Test3", "Test3", 0.0, "Test3"))))
    private val result4: Result<ResultsDto.SeriesResults> = Result.Success(ResultsDto.SeriesResults(listOf(SeriesDto("Test3", "Test3", 0.0, "Test3"))))
    private val fakeCategoriesApi = FakeCategoriesApi(result1, result2, result3, result4)

    @Before
    fun setupRepository() {
        val scope = TestScope()
        val fakeMoviesDao = FakeMoviesDao()
        repository = CategoriesRepositoryImpl(
            fakeCategoriesApi, fakeMoviesDao, FakeCategoriesDao(fakeMoviesDao), TaskManager(scope)
        )
    }

    @Test
    fun `should successfully take a list of categories`() = runTest(StandardTestDispatcher()) {
        val result = mutableListOf<ResultCategories>()
        TestScope().launch {
            repository.getCategories().collect {
                result.add(it)
            }
        }
        advanceUntilIdle()
        assertEquals(
            listOf(
                ResultCategories(listOf(), null),
                ResultCategories(listOf(Category(CategoryName(1, 1), listOf(Movie(1, "TestId", "Test", 0.0, "Test", Type.Movies)))), null)
            ), result
        )
    }

    @Test
    fun `should receive a ResultCategories with a network error in case of a network errors in all requests`() = runTest(StandardTestDispatcher()) {
        fakeCategoriesApi.setUpcomingSeriesListResult(Result.Failure.NetworkError)
        fakeCategoriesApi.setUpcomingMoviesListResult(Result.Failure.NetworkError)
        fakeCategoriesApi.setTrendingSeriesListResult(Result.Failure.NetworkError)
        fakeCategoriesApi.setTrendingMoviesListResult(Result.Failure.NetworkError)
        val result = mutableListOf<ResultCategories>()
        TestScope().launch {
            repository.getCategories().collect {
                result.add(it)
            }
        }
        advanceUntilIdle()
        assertResultsWithNetworkErrorEquals(result)
    }

    @Test
    fun `should receive a ResultCategories with a network error in case of a network error in the TrendingSeries category request`() =
        runTest(StandardTestDispatcher()) {
            fakeCategoriesApi.setTrendingSeriesListResult(Result.Failure.NetworkError)
            val result = mutableListOf<ResultCategories>()
            TestScope().launch {
                repository.getCategories().collect {
                    result.add(it)
                }
            }
            advanceUntilIdle()
            assertResultsWithNetworkErrorEquals(result)
        }

    @Test
    fun `should receive a ResultCategories with a other error in case of a other errors in all requests`() = runTest(StandardTestDispatcher()) {
        fakeCategoriesApi.setUpcomingSeriesListResult(Result.Failure.OtherError)
        fakeCategoriesApi.setUpcomingMoviesListResult(Result.Failure.OtherError)
        fakeCategoriesApi.setTrendingSeriesListResult(Result.Failure.OtherError)
        fakeCategoriesApi.setTrendingMoviesListResult(Result.Failure.OtherError)
        val result = mutableListOf<ResultCategories>()
        TestScope().launch {
            repository.getCategories().collect {
                result.add(it)
            }
        }
        advanceUntilIdle()
        assertResultsWithOtherErrorEquals(result)
    }

    @Test
    fun `should receive a ResultCategories with a network error in case of a other error in the TrendingSeries category request`() =
        runTest(StandardTestDispatcher()) {
            fakeCategoriesApi.setTrendingSeriesListResult(Result.Failure.OtherError)
            val result = mutableListOf<ResultCategories>()
            TestScope().launch {
                repository.getCategories().collect {
                    result.add(it)
                }
            }
            advanceUntilIdle()
            assertResultsWithOtherErrorEquals(result)
        }

    private fun assertResultsWithNetworkErrorEquals(actual: List<ResultCategories>) {
        val expected = listOf(
            ResultCategories(listOf(), null), ResultCategories(listOf(), Result.Failure.NetworkError)
        )
        assertEquals(expected, actual)
    }

    private fun assertResultsWithOtherErrorEquals(actual: List<ResultCategories>) {
        val expected = listOf(
            ResultCategories(listOf(), null), ResultCategories(listOf(), Result.Failure.OtherError)
        )
        assertEquals(expected, actual)
    }
}