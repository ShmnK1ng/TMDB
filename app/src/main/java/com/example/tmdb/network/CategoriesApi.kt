package com.example.tmdb.network

import com.example.tmdb.data.model.ResultsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesApi {

    @GET("trending/movie/week")
    suspend fun getTrendingMoviesList(): Result<ResultsDto.MovieResults>

    @GET("trending/tv/week")
    suspend fun getTrendingSeriesList(): Result<ResultsDto.SeriesResults>

    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(
        @Query("page") page: Int
    ): Result<ResultsDto.MovieResults>

    @GET("tv/on_the_air")
    suspend fun getUpcomingSeriesList(
        @Query("page") page: Int
    ): Result<ResultsDto.SeriesResults>
}