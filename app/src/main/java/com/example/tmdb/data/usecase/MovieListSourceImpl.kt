package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.repository.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieListSourceImpl @Inject constructor(private val repository: CategoriesRepository) : MovieListSource {
    override suspend fun getCategories(): List<Category> = withContext(Dispatchers.IO) {
        val categoriesList = mutableListOf<Category>()
        categoriesList.add(repository.fetchTrendingMoviesCategory())
        categoriesList.add(repository.fetchTrendingSeriesCategory())
        categoriesList.add(repository.fetchUpcomingMoviesCategories())
        categoriesList.add(repository.fetchUpcomingSeriesCategories())
        return@withContext categoriesList.toList()
    }
}