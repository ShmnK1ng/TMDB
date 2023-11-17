package com.example.tmdb.data.repository

import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieResultsDto
import com.example.tmdb.data.model.MoviesDao
import com.example.tmdb.data.model.ResultCategories
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
import com.example.tmdb.network.asFailure
import com.example.tmdb.network.asSuccess
import com.example.tmdb.network.isSuccess
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao,
    private val taskManager: TaskManager
) : CategoriesRepository {

    override fun getCategories(): Flow<ResultCategories> {
        val errorsFlow = MutableStateFlow(ResultCategories(emptyList(), null))
        taskManager.startTask { updateCategoriesDB(errorsFlow) }
        return merge(getCategoriesFromDB(), errorsFlow)

    }

    private suspend fun <T> processApiResult(result: com.example.tmdb.network.Result<T>, errorsFlow: MutableStateFlow<ResultCategories>): List<Movie>? {
        return if (result.isSuccess()) {
            val movieList = when (val data = result.asSuccess().value) {
                is MovieResultsDto -> data.result.moviesDtoListToMovieList()
                is SeriesResultsDto -> data.result.seriesDtoListToMovieList()
                else -> null
            }
            movieList
        } else {
            errorsFlow.emit(ResultCategories(emptyList(), result.asFailure().error))
            null
        }
    }

    private suspend fun getMoviesListAsync(id: Int, errorsFlow: MutableStateFlow<ResultCategories>): Deferred<List<Movie>?>? = coroutineScope {
        when (id) {
            TRENDING_MOVIES_ID -> async {
                val result = api.getTrendingMoviesList()
                processApiResult(result, errorsFlow)
            }
            TRENDING_SERIES_ID -> async {
                val result = api.getTrendingSeriesList()
                processApiResult(result, errorsFlow)
            }
            UPCOMING_MOVIES_ID -> async {
                val result = api.getUpcomingMoviesList(1)
                processApiResult(result, errorsFlow)
            }
            UPCOMING_SERIES_ID -> async {
                val result = api.getUpcomingSeriesList(1)
                processApiResult(result, errorsFlow)
            }
            else -> {
                null
            }
        }
    }

    private suspend fun updateCategoriesDB(errorsFlow: MutableStateFlow<ResultCategories>) {
        val categoriesNames: List<CategoryName> =
            categoriesDao.getAllCategoryNames().toCategoryNamesList()
        val categoriesList = categoriesNames.mapNotNull { categoryName ->
            val moviesList = getMoviesListAsync(categoryName.id, errorsFlow)?.await()
            if (moviesList != null) {
                Category(categoryName, moviesList)
            } else {
                null
            }
        }
        moviesDao.saveMoviesAndDependencies(categoriesList)
    }

    private fun getCategoriesFromDB(): Flow<ResultCategories> {
        return categoriesDao.getCategoriesAndMovies().map {
            it.map { categoryAndMovies ->
                Category(
                    categoryAndMovies.categoryEntity.toCategoryName(),
                    categoryAndMovies.moviesEntity.toMovieList()
                )
            }
        }.map { categoryList ->
            ResultCategories(categoryList, null)
        }
    }
}

    interface CategoriesRepository {
        fun getCategories(): Flow<ResultCategories>
    }