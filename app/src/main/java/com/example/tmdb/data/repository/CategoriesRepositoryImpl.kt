package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao
) : CategoriesRepository {

    override suspend fun getCategories(): List<Category> = coroutineScope {
        val categoriesNames: List<CategoryName> =
            categoriesDao.getAllCategoryNames().toCategoryNamesList()
        val categoriesList = categoriesNames.map { categoryName ->
            Category(categoryName, getMoviesListAsync(categoryName.id)?.await())
        }
        categoriesList.forEach { category ->
            val categoryId = category.categoryName.id
            moviesDao.saveMoviesAndDependencies(category.toMovieEntityList(), categoryId)
        }
        categoriesList
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
}

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
}