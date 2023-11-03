package com.example.tmdb.network

import com.example.tmdb.data.model.MovieOverviewDto
import com.example.tmdb.data.model.SeriesOverviewDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieOverviewApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieOverview(
        @Path("movie_id") movieId: Int
    ): MovieOverviewDto

    @GET("tv/{series_id}")
    suspend fun getSeriesOverview(
        @Path("series_id") movieId: Int
    ): SeriesOverviewDto
}