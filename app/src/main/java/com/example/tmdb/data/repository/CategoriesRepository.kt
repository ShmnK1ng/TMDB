package com.example.tmdb.data.repository

import com.example.tmdb.data.model.Category
import kotlinx.coroutines.CoroutineScope

interface CategoriesRepository {
   suspend fun getCategories(scope: CoroutineScope): List<Category>
}