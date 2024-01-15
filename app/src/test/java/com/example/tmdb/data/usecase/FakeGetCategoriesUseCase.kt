package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.ResultCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetCategoriesUseCase(private val result: ResultCategories) : GetCategoriesUseCase {
    override fun getCategories(): Flow<ResultCategories> {
        return flowOf(
            result
        )
    }
}