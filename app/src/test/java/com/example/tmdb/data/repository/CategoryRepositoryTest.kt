package com.example.tmdb.data.repository

import com.example.tmdb.MainCoroutineRule
import com.example.tmdb.collectForTest
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryAndMovies
import com.example.tmdb.data.model.CategoryEntity
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.CategoriesDaoStub
import com.example.tmdb.data.model.MoviesDaoStub
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieDto
import com.example.tmdb.data.model.MovieEntity
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.ResultsDto
import com.example.tmdb.data.model.SeriesDto
import com.example.tmdb.data.model.Type
import com.example.tmdb.data.model.utils.TaskManager
import com.example.tmdb.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryRepositoryTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: CategoriesRepositoryImpl
    private val expectedResultOfTrendingMovies: Result<ResultsDto.MovieResults> = Result.Success(ResultsDto.MovieResults(listOf(MovieDto("Test1", "Test1", 0.0, "Test1"))))
    private val expectedResultOfTrendingSeries: Result<ResultsDto.SeriesResults> = Result.Success(ResultsDto.SeriesResults(listOf(SeriesDto("Test2", "Test2", 0.0, "Test2"))))
    private val expectedResultOfUpcomingMovies: Result<ResultsDto.MovieResults> = Result.Success(ResultsDto.MovieResults(listOf(MovieDto("Test3", "Test3", 0.0, "Test3"))))
    private val expectedResultOfUpcomingSeries: Result<ResultsDto.SeriesResults> = Result.Success(ResultsDto.SeriesResults(listOf(SeriesDto("Test3", "Test3", 0.0, "Test3"))))
    private val fakeCategoriesApi = CategoriesApiStub(expectedResultOfTrendingMovies, expectedResultOfTrendingSeries, expectedResultOfUpcomingMovies, expectedResultOfUpcomingSeries)
    private val scope = TestScope(UnconfinedTestDispatcher())
    private val savedListOfCategoriesAndMovies = listOf(
        CategoryAndMovies(
            CategoryEntity(1, 1), listOf(MovieEntity(1, "TestId", "Test", 0.0, "Test", Type.Movies))
        )
    )

    @Before
    fun setupRepository() {
        val fakeMoviesDao = MoviesDaoStub(savedListOfCategoriesAndMovies)
        repository = CategoriesRepositoryImpl(
            fakeCategoriesApi, fakeMoviesDao, CategoriesDaoStub(fakeMoviesDao), TaskManager(scope)
        )
    }

    @Test
    fun `should return list of categories and null from errors`() {
        val actualResult = repository.getCategories().collectForTest(scope)
        assertEquals(
            listOf(
                ResultCategories(listOf(Category(CategoryName(1, 1), listOf(Movie(1, "TestId", "Test", 0.0, "Test", Type.Movies)))), null)
            ), actualResult
        )
    }

    @Test
    fun `should return a ResultCategories with  a network error in case of a network errors in all requests`() {
        fakeCategoriesApi.expectedResultOfTrendingMovies = Result.Failure.NetworkError
        fakeCategoriesApi.expectedResultOfTrendingSeries = Result.Failure.NetworkError
        fakeCategoriesApi.expectedResultOfUpcomingMovies = Result.Failure.NetworkError
        fakeCategoriesApi.expectedResultOfUpcomingSeries = Result.Failure.NetworkError
        val result = repository.getCategories().collectForTest(scope)
        assertResultsWithNetworkErrorEquals(result)
    }

    @Test
    fun `should return a ResultCategories with empty list of categories and a network error in case of a network error in the TrendingSeries category request`() {
        fakeCategoriesApi.expectedResultOfTrendingSeries = Result.Failure.NetworkError
        val result = repository.getCategories().collectForTest(scope)
        assertResultsWithNetworkErrorEquals(result)
    }

    @Test
    fun `should return a ResultCategories with list of categories and a network error in case of a network error in the TrendingSeries category request`() {
        fakeCategoriesApi.expectedResultOfTrendingSeries = Result.Failure.NetworkError

        val result = repository.getCategories().collectForTest(scope)
        assertResultsWithNetworkErrorEquals(result)
    }

    @Test
    fun `should return a ResultCategories with empty list of categories and an other error`() {
        fakeCategoriesApi.expectedResultOfTrendingMovies = Result.Failure.OtherError
        fakeCategoriesApi.expectedResultOfTrendingSeries = Result.Failure.OtherError
        fakeCategoriesApi.expectedResultOfUpcomingMovies = Result.Failure.OtherError
        fakeCategoriesApi.expectedResultOfUpcomingSeries = Result.Failure.OtherError
        val result = repository.getCategories().collectForTest(scope)
        assertResultsWithOtherErrorEquals(result)
    }

    @Test
    fun `should return a ResultCategories with empty list of categories and a network error`()
        {
            fakeCategoriesApi.expectedResultOfTrendingSeries = Result.Failure.OtherError
            val result = repository.getCategories().collectForTest(scope)
            assertResultsWithOtherErrorEquals(result)
        }

    private fun assertResultsWithNetworkErrorEquals(actual: List<ResultCategories>) {
        val expected = listOf(
            ResultCategories(emptyList(), Result.Failure.NetworkError)
        )
        assertEquals(expected, actual)
    }

    private fun assertResultsWithOtherErrorEquals(actual: List<ResultCategories>) {
        val expected = listOf(
            ResultCategories(emptyList(), Result.Failure.OtherError)
        )
        assertEquals(expected, actual)
    }
}