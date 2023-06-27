package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category
import kotlinx.coroutines.CoroutineScope

interface GetCategoriesUseCase {
    suspend fun getCategories(scope: CoroutineScope): List<Category>
}