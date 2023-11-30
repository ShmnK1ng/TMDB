package com.example.tmdb.data.model

import com.example.tmdb.network.Result

data class ResultCategory(
    val result: Result<ResultsDto>?,
    val categoryName: CategoryName
)