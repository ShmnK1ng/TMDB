package com.example.tmdb.data.model

import com.example.tmdb.network.Result

data class ResultCategories(
    val listCategories: List<Category>,
    val error: Result.Failure<*>?
)