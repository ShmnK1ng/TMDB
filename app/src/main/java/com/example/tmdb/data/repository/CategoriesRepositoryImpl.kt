package com.example.tmdb.data.repository

import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.MovieResultsDto
import com.example.tmdb.data.model.MoviesDao
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.ResultMoviesList
import com.example.tmdb.data.model.SeriesResultsDto
import com.example.tmdb.data.model.TRENDING_MOVIES_ID
import com.example.tmdb.data.model.TRENDING_SERIES_ID
import com.example.tmdb.data.model.UPCOMING_MOVIES_ID
import com.example.tmdb.data.model.UPCOMING_SERIES_ID
import com.example.tmdb.data.model.moviesDtoListToMovieList
import com.example.tmdb.data.model.seriesDtoListToMovieList
import com.example.tmdb.data.model.toCategoryName
import com.example.tmdb.data.model.toCategoryNamesList
import com.example.tmdb.data.model.toMovieList
import com.example.tmdb.data.model.utils.TaskManager
import com.example.tmdb.network.CategoriesApi
import com.example.tmdb.network.Result
import com.example.tmdb.network.asFailure
import com.example.tmdb.network.asSuccess
import com.example.tmdb.network.isSuccess
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao,
    private val taskManager: TaskManager
) : CategoriesRepository {

    override fun getCategories(): Flow<ResultCategories> {
        val errorsFlow: MutableStateFlow<Result.Failure<*>?> = MutableStateFlow(null)
        taskManager.startTask { updateCategoriesDB(errorsFlow) }
        return getCategoriesFromDB().combine(errorsFlow) { categoriesList, error ->
            ResultCategories(categoriesList, error)
        }
    }

    private fun <T> processApiResult(result: Result<T>): ResultMoviesList {
        return if (result.isSuccess()) {
            val movieList = when (val data = result.asSuccess().value) {
                is MovieResultsDto -> data.result.moviesDtoListToMovieList()
                is SeriesResultsDto -> data.result.seriesDtoListToMovieList()
                else -> null
            }
            ResultMoviesList(movieList, null)
        } else {
            ResultMoviesList(null, result.asFailure())
        }
    }

    private suspend fun getMoviesListAsync(id: Int): Deferred<ResultMoviesList> = coroutineScope {
        when (id) {
            TRENDING_MOVIES_ID -> async {
                val result = api.getTrendingMoviesList()
                processApiResult(result)
            }

            TRENDING_SERIES_ID -> async {
                val result = api.getTrendingSeriesList()
                processApiResult(result)
            }

            UPCOMING_MOVIES_ID -> async {
                val result = api.getUpcomingMoviesList(1)
                processApiResult(result)
            }

            UPCOMING_SERIES_ID -> async {
                val result = api.getUpcomingSeriesList(1)
                processApiResult(result)
            }

            else -> {
                async {
                    ResultMoviesList(null, null)
                }
            }
        }
    }

    private suspend fun updateCategoriesDB(errorsFlow: MutableStateFlow<Result.Failure<*>?>) {
        val categoriesNames: List<CategoryName> =
            categoriesDao.getAllCategoryNames().toCategoryNamesList()
        var moviesListError: Result.Failure<*>? = null
        val categoriesList = categoriesNames.mapNotNull { categoryName ->
            val resultMoviesList = getMoviesListAsync(categoryName.id).await()
            if (resultMoviesList.error != null) {
                if (moviesListError != Result.Failure.OtherError())
                    moviesListError = resultMoviesList.error
            }
            if (resultMoviesList.moviesList != null) {
                Category(categoryName, resultMoviesList.moviesList)
            } else {
                null
            }
        }
        errorsFlow.emit(moviesListError)
        moviesDao.saveMoviesAndDependencies(categoriesList)
    }

    private fun getCategoriesFromDB(): Flow<List<Category>> {
        return categoriesDao.getCategoriesAndMovies().map {
            it.map { categoryAndMovies ->
                Category(
                    categoryAndMovies.categoryEntity.toCategoryName(),
                    categoryAndMovies.moviesEntity.toMovieList()
                )
            }
        }
    }
}

interface CategoriesRepository {
    fun getCategories(): Flow<ResultCategories>
}