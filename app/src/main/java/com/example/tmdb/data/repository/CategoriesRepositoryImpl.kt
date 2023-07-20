package com.example.tmdb.data.repository

import com.example.tmdb.data.model.*
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val api: CategoriesApi, private val moviesDao: MoviesDao, private val categoriesDao: CategoriesDao ) :
    CategoriesRepository {

    override suspend fun getCategories(): List<Category> = coroutineScope {
        saveCategories()
        val trendingMovieList =
            async { api.getTrendingMoviesList().result.moviesDtoListToMovieList() }
        val trendingSeriesList =
            async { api.getTrendingSeriesList().result.seriesDtoListToMovieList() }
        val upcomingMoviesList =
            async { api.getUpcomingMoviesList(1).result.moviesDtoListToMovieList() }
        val upcomingSeriesList =
            async { api.getUpcomingSeriesList(1).result.seriesDtoListToMovieList() }
        val categoriesList = listOf(
            Category(CategoryName.TrendingMovies, trendingMovieList.await()),
            Category(CategoryName.TrendingSeries, trendingSeriesList.await()),
            Category(CategoryName.UpcomingMovies, upcomingMoviesList.await()),
            Category(CategoryName.UpcomingSeries, upcomingSeriesList.await())
        )
        categoriesList.forEach { category ->
            val categoryId = category.categoryName.id
           category.toMovieEntityList().forEach {
               moviesDao.saveMovie(it)
               moviesDao.saveCategoryAndMovieDependency(
                   CategoryAndMoviesDependenciesEntity(
                       categoryId = categoryId,
                       movieId = it.id
                   )
               )
           }
        }
        categoriesList
    }

    override suspend fun saveCategories() {
        categoriesDao.apply {
            saveCategory(CategoryEntity(CategoryName.TrendingMovies.id, CategoryName.TrendingMovies.name))
            saveCategory(CategoryEntity(CategoryName.TrendingSeries.id, CategoryName.TrendingSeries.name))
            saveCategory(CategoryEntity(CategoryName.UpcomingMovies.id, CategoryName.UpcomingMovies.name))
            saveCategory(CategoryEntity(CategoryName.UpcomingSeries.id, CategoryName.UpcomingSeries.name))
        }
    }
}

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
    suspend fun saveCategories()
}