package com.example.tmdb.data.model

data class ResultCategories(
    val listCategories: List<Category>,
    val error: Throwable?
)