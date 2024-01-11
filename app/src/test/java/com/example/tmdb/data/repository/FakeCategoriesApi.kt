package com.example.tmdb.data.repository

import com.example.tmdb.data.model.ResultsDto
import com.example.tmdb.network.CategoriesApi
import com.example.tmdb.network.Result

class FakeCategoriesApi(
    private var result1: Result<ResultsDto.MovieResults>,
    private var result2: Result<ResultsDto.SeriesResults>,
    private var result3: Result<ResultsDto.MovieResults>,
    private var result4: Result<ResultsDto.SeriesResults>
    ) : CategoriesApi {

    override suspend fun getTrendingMoviesList(): Result<ResultsDto.MovieResults> {
       return result1
    }

    override suspend fun getTrendingSeriesList(): Result<ResultsDto.SeriesResults> {
        return result2
    }

    override suspend fun getUpcomingMoviesList(page: Int): Result<ResultsDto.MovieResults> {
        return result3
    }

    override suspend fun getUpcomingSeriesList(page: Int): Result<ResultsDto.SeriesResults> {
        return result4
    }

    fun setTrendingMoviesListResult(newResult: Result<ResultsDto.MovieResults>) {
        result1 = newResult
    }

    fun setTrendingSeriesListResult(newResult: Result<ResultsDto.SeriesResults>) {
        result2 = newResult
    }

    fun setUpcomingMoviesListResult(newResult: Result<ResultsDto.MovieResults>) {
        result3 = newResult
    }

    fun setUpcomingSeriesListResult(newResult: Result<ResultsDto.SeriesResults>) {
        result4 = newResult
    }
}