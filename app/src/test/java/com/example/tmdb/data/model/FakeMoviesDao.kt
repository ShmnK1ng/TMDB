package com.example.tmdb.data.model

import kotlinx.coroutines.flow.MutableStateFlow


class FakeMoviesDao : MoviesDao() {

    val flow: MutableStateFlow<List<CategoryAndMovies>> = MutableStateFlow(listOf())
    private val testMovieEntity = MovieEntity(1, "TestId", "Test", 0.0, "Test", Type.Movies)

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
            listOf(
                CategoryAndMovies(
                    CategoryEntity(1, 1), listOf(testMovieEntity)
                )
            )
        )
    }
}