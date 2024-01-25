package com.example.tmdb.data.model

import kotlinx.coroutines.flow.Flow

class CategoriesDaoStub(private val moviesDao: MoviesDaoStub) : CategoriesDao {


    override suspend fun getAllCategoryNames(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(1,1), CategoryEntity(2,2), CategoryEntity(3,3), CategoryEntity(4,4)
        )
    }

    override fun getCategoriesAndMovies(): Flow<List<CategoryAndMovies>> {
        return moviesDao.flow
    }
}