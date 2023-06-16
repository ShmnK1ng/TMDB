package com.example.tmdb.data.repository

import com.example.tmdb.data.model.Category
import com.example.tmdb.network.MovieApi
import com.example.tmdb.ui.home.utils.LANGUAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val api: MovieApi) :
    CategoriesRepository {

    override suspend fun fetchTrendingMoviesCategory(): Category = withContext(Dispatchers.IO) {
        val results = api.getTrendingMoviesList("movie", LANGUAGE).toMovieList()
        Category("Trending movies", results )
    }

    override suspend fun fetchTrendingSeriesCategory(): Category = withContext(Dispatchers.IO) {
        val results = api.getTrendingSeriesList("tv", LANGUAGE).toMovieList()
        Category("Trending series", results )
    }

    override suspend fun fetchUpcomingMoviesCategories(): Category =
        withContext(Dispatchers.IO) {
            val results = api.getUpcomingMoviesList(LANGUAGE, 1).toMovieList()
            Category("Upcoming movies", results)
        }

    override suspend fun fetchUpcomingSeriesCategories(): Category =
        withContext(Dispatchers.IO) {
            val results = api.getUpcomingSeriesList(LANGUAGE, 1).toMovieList()
            Category("Upcoming series", results)
        }
}