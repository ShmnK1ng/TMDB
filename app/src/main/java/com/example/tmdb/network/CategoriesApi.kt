package com.example.tmdb.network

import com.example.tmdb.data.model.MovieResultsDto
import com.example.tmdb.data.model.SeriesResultsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesList(): Result<MovieResultsDto>

    @GET("trending/tv/week")
    suspend fun getTrendingSeriesList(): Result<SeriesResultsDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(
        @Query("page") page: Int
    ): Result<MovieResultsDto>

    @GET("tv/on_the_air")
    suspend fun getUpcomingSeriesList(
        @Query("page") page: Int
    ): Result<SeriesResultsDto>
}