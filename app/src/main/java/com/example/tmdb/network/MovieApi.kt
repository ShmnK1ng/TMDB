package com.example.tmdb.network

import com.example.tmdb.data.model.MovieResultsDto
import com.example.tmdb.data.model.SeriesResultsDto
import com.example.tmdb.ui.home.utils.AUTHORIZATION
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @Headers(
        "Authorization: $AUTHORIZATION"
    )
    @GET("trending/{category}/week")
    suspend fun getTrendingMoviesList(
        @Path("category") category : String,
        @Query("language") language: String,
    ) : MovieResultsDto

    @Headers(
        "Authorization: $AUTHORIZATION"
    )
    @GET("trending/{category}/week")
    suspend fun getTrendingSeriesList(
        @Path("category") category : String,
        @Query("language") language: String,
    ) : SeriesResultsDto

    @Headers(
        "Authorization: $AUTHORIZATION"
    )
    @GET("movie/upcoming")
    suspend fun getUpcomingMoviesList(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MovieResultsDto

    @Headers(
        "Authorization: $AUTHORIZATION"
    )
    @GET("tv/on_the_air")
    suspend fun getUpcomingSeriesList(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : SeriesResultsDto
}