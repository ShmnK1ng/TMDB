package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import com.example.tmdb.data.usecase.GetMovieOverviewUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*

class MovieOverviewViewModel @AssistedInject constructor(
    getMovieOverviewUseCase: GetMovieOverviewUseCase,
    @Assisted
    movie: Movie
) : ViewModel() {

    private val _movieOverview: MutableStateFlow<MovieOverview?> = MutableStateFlow(null)
    val movieOverview: Flow<MovieOverview?> = _movieOverview.asStateFlow()

    init {
        getMovieOverviewUseCase.getMovieOverview(movie).onEach {
            _movieOverview.value = it
        }
            .launchIn(viewModelScope)
    }

    @AssistedFactory
    interface Factory {
        fun create(movie: Movie): MovieOverviewViewModel
    }

    companion object {
        fun provideMovieOverviewViewModelFactory(factory: Factory, movie: Movie) : ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return factory.create(movie) as T
                }
            }
        }
    }
}