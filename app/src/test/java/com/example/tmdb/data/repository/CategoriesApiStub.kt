package com.example.tmdb.data.repository

import com.example.tmdb.data.model.ResultsDto
import com.example.tmdb.network.CategoriesApi
import com.example.tmdb.network.Result

class CategoriesApiStub(
    var expectedResultOfTrendingMovies: Result<ResultsDto.MovieResults>,
    var expectedResultOfTrendingSeries: Result<ResultsDto.SeriesResults>,
    var expectedResultOfUpcomingMovies: Result<ResultsDto.MovieResults>,
    var expectedResultOfUpcomingSeries: Result<ResultsDto.SeriesResults>
) : CategoriesApi {

    override suspend fun getTrendingMoviesList(): Result<ResultsDto.MovieResults> {
        return expectedResultOfTrendingMovies
    }

    override suspend fun getTrendingSeriesList(): Result<ResultsDto.SeriesResults> {
        return expectedResultOfTrendingSeries
    }

    override suspend fun getUpcomingMoviesList(page: Int): Result<ResultsDto.MovieResults> {
        return expectedResultOfUpcomingMovies
    }

    override suspend fun getUpcomingSeriesList(page: Int): Result<ResultsDto.SeriesResults> {
        return expectedResultOfUpcomingSeries
    }
}