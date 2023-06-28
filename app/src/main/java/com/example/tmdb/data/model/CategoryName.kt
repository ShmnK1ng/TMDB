package com.example.tmdb.data.model

sealed class CategoryName(val value: String) {
    object TrendingMovies : CategoryName("Trending movies")
    object TrendingSeries : CategoryName("Trending series")
    object UpcomingMovies : CategoryName("Upcoming movies")
    object UpcomingSeries : CategoryName("Upcoming series")
}