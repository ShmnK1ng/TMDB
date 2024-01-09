package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.CategoryName
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.ResultCategories
import com.example.tmdb.data.model.Type
import com.example.tmdb.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeGetCategoriesUseCase : GetCategoriesUseCase {
    override fun getCategories(): Flow<ResultCategories> {
        return MutableStateFlow(
            ResultCategories(
                listOf(
                    Category(CategoryName(1, 1), listOf(Movie(0, "remoteId", "Title1", 0.0, "posterPath", Type.Series)))
                ), Result.Failure.NetworkError
            )
        )
    }
}