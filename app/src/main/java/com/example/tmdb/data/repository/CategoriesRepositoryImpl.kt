package com.example.tmdb.data.repository

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.moviesDtoListToMovieList
import com.example.tmdb.data.model.seriesDtoListToMovieList
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val api: CategoriesApi) :
    CategoriesRepository {

    override suspend fun getCategories(): List<Category> = coroutineScope {
        val trendingMovieList =
            async { api.getTrendingMoviesList().result.moviesDtoListToMovieList() }
        val trendingSeriesList =
            async { api.getTrendingSeriesList().result.seriesDtoListToMovieList() }
        val upcomingMoviesList =
            async { api.getUpcomingMoviesList(1).result.moviesDtoListToMovieList() }
        val upcomingSeriesList =
            async { api.getUpcomingSeriesList(1).result.seriesDtoListToMovieList() }
        listOf(
            Category(CategoryName.TrendingMovies.nameId, trendingMovieList.await()),
            Category(CategoryName.TrendingSeries.nameId, trendingSeriesList.await()),
            Category(CategoryName.UpcomingMovies.nameId, upcomingMoviesList.await()),
            Category(CategoryName.UpcomingSeries.nameId, upcomingSeriesList.await())
        )
    }
}

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>
}