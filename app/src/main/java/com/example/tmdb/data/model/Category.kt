package com.example.tmdb.data.model

data class Category(
    val categoryName: String,
    val movieList: List<Movie>?
)