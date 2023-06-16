package com.example.tmdb.data.repository

import com.example.tmdb.data.model.Category

interface CategoriesRepository {
   suspend fun fetchTrendingMoviesCategory(): Category
   suspend fun fetchTrendingSeriesCategory(): Category
   suspend fun fetchUpcomingMoviesCategories(): Category
   suspend fun fetchUpcomingSeriesCategories(): Category
}