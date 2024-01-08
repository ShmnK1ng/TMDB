package com.example.tmdb.data.repository

import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.MoviesDao
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.ResultCategory
import com.example.tmdb.data.model.ResultsDto
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
import kotlinx.coroutines.awaitAll
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
        val errorsFlow: MutableStateFlow<Result.Failure?> = MutableStateFlow(null)
        taskManager.startTask { updateCategoriesDB(errorsFlow) }
        return getCategoriesFromDB().combine(errorsFlow) { categoriesList, error ->
            ResultCategories(categoriesList, error)
        }
    }

    private suspend fun getMoviesListAsync(id: Int): Result<ResultsDto> {
        return when (id) {
            TRENDING_MOVIES_ID -> api.getTrendingMoviesList()
            TRENDING_SERIES_ID -> api.getTrendingSeriesList()
            UPCOMING_MOVIES_ID -> api.getUpcomingMoviesList(1)
            UPCOMING_SERIES_ID -> api.getUpcomingSeriesList(1)
            else -> throw IllegalArgumentException("Unknown category!")
        }
    }

    private suspend fun updateCategoriesDB(errorsFlow: MutableStateFlow<Result.Failure?>) {
        val categoriesNames: List<CategoryName> = categoriesDao.getAllCategoryNames().toCategoryNamesList()
        val deferredResults: List<Deferred<ResultCategory>> = coroutineScope {
            categoriesNames.map { categoryName ->
                async { ResultCategory(getMoviesListAsync(categoryName.id), categoryName) }
            }
        }
        val results = awaitAll(*deferredResults.toTypedArray())
        val error = results.firstOrNull { !it.result.isSuccess() }?.result?.asFailure()
        if (error != null) {
            errorsFlow.emit(error)
        } else {
            val categoriesList = results.map {
                val movieList = when (val data = it.result.asSuccess().value) {
                    is ResultsDto.MovieResults -> data.result.moviesDtoListToMovieList()
                    is ResultsDto.SeriesResults -> data.result.seriesDtoListToMovieList()
                }
                Category(it.categoryName, movieList)
            }
            moviesDao.saveMoviesAndDependencies(categoriesList)
        }
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