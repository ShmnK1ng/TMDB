package com.example.tmdb.data.model

data class Category(
    val categoryName: CategoryName,
    val movieList: List<Movie>?
)