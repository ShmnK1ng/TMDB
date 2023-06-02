package com.example.tmdb.data

import com.example.tmdb.data.model.Category

interface DataRepository {
    fun getCategories(): List<Category>
}