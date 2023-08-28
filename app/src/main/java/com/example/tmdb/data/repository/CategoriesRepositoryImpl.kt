package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao,
    private val coroutineScope: CoroutineScope
) : CategoriesRepository {
    private var previousJob: Job? = null

    override suspend fun getCategories(): Flow<List<Category>> {
        if (previousJob?.isActive == false || previousJob == null) {
            previousJob?.cancel()
            previousJob =  coroutineScope.launch { updateCategoriesDB() }
        }
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
        moviesDao.deleteOldMovies()
        categoriesList.forEach { category ->
            val categoryId = category.categoryName.id
            moviesDao.saveMoviesAndDependencies(category.toMovieEntityList(), categoryId)
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
    suspend fun getCategories(): Flow<List<Category>>
}