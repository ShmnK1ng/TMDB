package com.example.tmdb.data

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.Movie
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor() : DataRepository {

    private val movie1 = Movie("title 1", 5.0)
    private val movie2 = Movie("title 2", 6.0)
    private val movie3 = Movie("title 3", 8.0)
    private val movie4 = Movie("title 4", 6.0)
    private val movie5 = Movie("title 5", 7.0)
    private val movie6 = Movie("title 6", 9.0)
    private val movie7 = Movie("title 6", 9.0)
    private val moviesList = listOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7)

    private val trendingMovies = Category("Trending movies", moviesList)
    private val upcomingMovies = Category("Upcoming movies", moviesList)
    private val trendingTVShows = Category("Trending TV Shows", moviesList)
    private val upcomingTVShows = Category("Upcoming TV Shows", moviesList)
    override fun getCategories(): List<Category> {
        return listOf(trendingMovies, upcomingMovies, trendingTVShows, upcomingTVShows)
    }
}