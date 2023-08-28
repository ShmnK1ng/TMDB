package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(private val repository: CategoriesRepository) : GetCategoriesUseCase {
    override suspend fun getCategories(): Flow<List<Category>> {
        return repository.getCategories()
    }
}

interface GetCategoriesUseCase {
    suspend fun getCategories(): Flow<List<Category>>
}