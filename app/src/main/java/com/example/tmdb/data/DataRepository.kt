package com.example.tmdb.data

interface DataRepository {
    fun getCategories(): List<Category>
}