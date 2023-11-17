package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(private val repository: CategoriesRepository) : GetCategoriesUseCase {
    override fun getCategories(): Flow<ResultCategories> {
        return repository.getCategories()
    }
}

interface GetCategoriesUseCase {
    fun getCategories(): Flow<ResultCategories>
}