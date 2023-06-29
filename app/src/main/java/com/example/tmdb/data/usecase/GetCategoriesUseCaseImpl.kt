package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(private val repository: CategoriesRepository) : GetCategoriesUseCase {
    override suspend fun getCategories(): List<Category>  {
        return repository.getCategories()
    }
}

interface GetCategoriesUseCase {
    suspend fun getCategories(): List<Category>
}