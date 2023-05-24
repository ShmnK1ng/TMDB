package com.example.tmdb.data

data class Category(
    val categoryName: String,
    val movieList: List<Movie>?
)