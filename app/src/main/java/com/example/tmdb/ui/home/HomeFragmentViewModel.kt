package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import com.example.tmdb.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: Flow<List<Category>> = _categories.asStateFlow()
    private val _goToMovieOverview: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val goToMovieOverview: Flow<Movie?> = _goToMovieOverview.asStateFlow()
    private val _showError: MutableStateFlow<Result.Failure?> = MutableStateFlow(null)
    val showError: Flow<Result.Failure?> = _showError.asStateFlow()
    private var lastError: Result.Failure? = null

    fun onMovieItemClicked(movie: Movie) {
        _goToMovieOverview.value = movie
    }

    fun resetClickState() {
        _goToMovieOverview.value = null
    }

    fun resetErrorState() {
        _showError.value = null
    }

    init {
        getCategoriesUseCase.getCategories()
            .onEach { resultCategories ->
                if (lastError == null) {
                    lastError = resultCategories.error
                    _showError.value = resultCategories.error
                }
                _categories.value = resultCategories.listCategories
            }
            .launchIn(viewModelScope)
    }
}