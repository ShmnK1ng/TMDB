package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category

interface MovieListSource {
    suspend fun getCategories(): List<Category>
}