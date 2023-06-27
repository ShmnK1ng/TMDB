package com.example.tmdb.data.repository

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieDto
import com.example.tmdb.data.model.SeriesDto
import com.example.tmdb.network.CategoriesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(private val api: CategoriesApi) :
    CategoriesRepository {

    override suspend fun getCategories(scope: CoroutineScope): List<Category> {
        val trendingMovieList =
            scope.async { api.getTrendingMoviesList().result.moviesDtoListToMovieList() }
        val trendingSeriesList =
            scope.async { api.getTrendingSeriesList().result.seriesDtoListToMovieList() }
        val upcomingMoviesList =
            scope.async { api.getUpcomingMoviesList(1).result.moviesDtoListToMovieList() }
        val upcomingSeriesList =
            scope.async { api.getUpcomingSeriesList(1).result.seriesDtoListToMovieList() }
        return mutableListOf(
            Category("Trending movies", trendingMovieList.await()),
            Category("Trending series", trendingSeriesList.await()),
            Category("Upcoming movies", upcomingMoviesList.await()),
            Category("Upcoming series", upcomingSeriesList.await())
        ).toList()
    }

    private fun List<MovieDto>.moviesDtoListToMovieList(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        this.map {
            movieList.add(
                Movie(
                    id = it.id, title = it.title, rating = it.rating, posterPath = it.posterPath
                )
            )
        }
        return movieList.toList()
    }

    private fun List<SeriesDto>.seriesDtoListToMovieList(): List<Movie> {
        val movieList: MutableList<Movie> = mutableListOf()
        this.map {
            movieList.add(
                Movie(
                    id = it.id, title = it.name, rating = it.rating, posterPath = it.posterPath
                )
            )
        }
        return movieList.toList()
    }
}