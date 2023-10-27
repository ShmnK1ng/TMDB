package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.data.model.utils.TaskManager
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao,
    private val taskManager: TaskManager
) : CategoriesRepository {

    override fun getCategories(): Flow<List<Category>> {
        taskManager.startTask { updateCategoriesDB() }
        return getCategoriesFromDB()
    }

    private suspend fun getMoviesListAsync(id: Int): Deferred<List<Movie>>? = coroutineScope {
        when (id) {
            TRENDING_MOVIES_ID -> async { api.getTrendingMoviesList().result.moviesDtoListToMovieList() }
            TRENDING_SERIES_ID -> async { api.getTrendingSeriesList().result.seriesDtoListToMovieList() }
            UPCOMING_MOVIES_ID -> async { api.getUpcomingMoviesList(1).result.moviesDtoListToMovieList() }
            UPCOMING_SERIES_ID -> async { api.getUpcomingSeriesList(1).result.seriesDtoListToMovieList() }
            else -> {
                null
            }
        }
    }

    private suspend fun updateCategoriesDB() {
        val categoriesNames: List<CategoryName> =
            categoriesDao.getAllCategoryNames().toCategoryNamesList()
        val categoriesList = categoriesNames.map { categoryName ->
            Category(categoryName, getMoviesListAsync(categoryName.id)?.await())
        }
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
    fun getCategories(): Flow<List<Category>>
}