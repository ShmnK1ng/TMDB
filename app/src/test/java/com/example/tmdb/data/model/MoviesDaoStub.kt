package com.example.tmdb.data.model

import kotlinx.coroutines.flow.MutableStateFlow


class MoviesDaoStub(private val savedListOfCategoriesAndMovies: List<CategoryAndMovies>) : MoviesDao() {

    val flow: MutableStateFlow<List<CategoryAndMovies>> = MutableStateFlow(listOf())

    override suspend fun saveMovie(movieEntity: MovieEntity): Long {
        return 1
    }

    override suspend fun saveCategoryAndMovieDependency(categoryAndMoviesDependenciesEntity: CategoryAndMoviesDependenciesEntity) {

    }

    override fun getMovieByRemoteIdAndType(remoteId: String, type: Type): Int? {
        return null
    }

    override fun updateMovieByRemoteIdAndType(remoteId: String, type: Type, newTitle: String, newRating: Double, newPosterPath: String) {
       //do nothing
    }

    override suspend fun deleteMoviesByCategoryId(categoryId: Int) {
        //do nothing
    }

     override suspend fun saveMoviesAndDependencies(categoriesList: List<Category>?) {
        flow.emit(
            savedListOfCategoriesAndMovies
        )
    }
}