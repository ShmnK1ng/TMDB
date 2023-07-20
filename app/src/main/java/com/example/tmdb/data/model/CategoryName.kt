package com.example.tmdb.data.model

import com.example.tmdb.R

sealed class CategoryName(val name: Int, val id: Int) {
    object TrendingMovies : CategoryName(R.string.category_name_trending_movies, TRENDING_MOVIES_ID)
    object TrendingSeries : CategoryName(R.string.category_name_trending_series, TRENDING_SERIES_ID)
    object UpcomingMovies : CategoryName(R.string.category_name_upcoming_movies, UPCOMING_MOVIES_ID)
    object UpcomingSeries : CategoryName(R.string.category_name_upcoming_series, UPCOMING_SERIES_ID)
}