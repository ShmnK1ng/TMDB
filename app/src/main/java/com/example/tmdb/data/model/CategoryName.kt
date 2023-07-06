package com.example.tmdb.data.model

import com.example.tmdb.R

sealed class CategoryName(val nameId: Int) {
    object TrendingMovies : CategoryName(R.string.category_name_trending_movies)
    object TrendingSeries : CategoryName(R.string.category_name_trending_series)
    object UpcomingMovies : CategoryName(R.string.category_name_upcoming_movies)
    object UpcomingSeries : CategoryName(R.string.category_name_upcoming_series)
}