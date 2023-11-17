package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: Flow<List<Category>> = _categories.asStateFlow()
    private val _goToMovieOverview: MutableStateFlow<Movie?> = MutableStateFlow(null)
    val goToMovieOverview: Flow<Movie?> = _goToMovieOverview.asStateFlow()
    private val _showError: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val showError: Flow<Throwable?> = _showError.asStateFlow()

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
            .debounce(200)
            .onEach { resultCategories ->
                println(resultCategories.error)
                    if (resultCategories.error == null) {
                        _categories.value = resultCategories.listCategories
                    } else
                    {
                        _showError.value = resultCategories.error
                    }
            }
            .launchIn(viewModelScope)
    }
}