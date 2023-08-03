package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val api: CategoriesApi,
    private val moviesDao: MoviesDao,
    private val categoriesDao: CategoriesDao,
    private val coroutineScope: CoroutineScope
) : CategoriesRepository {
    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    private val categories: Flow<List<Category>> = _categories.asStateFlow()

    override suspend fun getCategories(): List<Category> = coroutineScope {
        val categoriesNames: List<CategoryName> =
            categoriesDao.getAllCategoryNames().toCategoryNamesList()
        val categoriesList = categoriesNames.map { categoryName ->
            Category(categoryName, getMoviesListAsync(categoryName.id)?.await())
        }
        updateCategoriesDB(categoriesList)
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

    private suspend fun updateCategoriesDB(categoriesList: List<Category>) {
        moviesDao.deleteOldMovies()
        categoriesList.forEach { category ->
            val categoryId = category.categoryName.id
            moviesDao.saveMoviesAndDependencies(category.toMovieEntityList(), categoryId)
        }
        coroutineScope.launch {
              _categories.value = categoriesList
        }
    }

    override suspend fun getFlow(): Flow<List<Category>> {
        return categories
    }
}

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getFlow(): Flow<List<Category>>
}