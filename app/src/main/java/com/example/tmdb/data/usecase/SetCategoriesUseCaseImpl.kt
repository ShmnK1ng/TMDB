package com.example.tmdb.data.usecase

import com.example.tmdb.data.model.CategoriesDao
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.MoviesDao
import javax.inject.Inject

class SetCategoriesUseCaseImpl @Inject constructor(val categoriesDao: CategoriesDao, val moviesDao: MoviesDao) : SetCategoriesUseCase  {
    override suspend fun setCategories(categoriesList: List<Category>) {
        TODO("Not yet implemented")
    }
}

interface SetCategoriesUseCase {
    suspend fun setCategories(categoriesList: List<Category>)
}