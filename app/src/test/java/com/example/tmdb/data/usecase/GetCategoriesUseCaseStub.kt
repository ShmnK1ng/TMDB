package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.ResultCategories
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCategoriesUseCaseStub : GetCategoriesUseCase {

    private val deferredResult = CompletableDeferred<ResultCategories>()

    fun setResult(result: ResultCategories) {
        deferredResult.complete(result)
    }

    override fun getCategories(): Flow<ResultCategories> {
        return flow {
            emit(deferredResult.await())
        }
    }
}