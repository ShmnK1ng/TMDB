package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Movie
import com.example.tmdb.data.model.MovieOverview
import com.example.tmdb.data.usecase.GetMovieOverviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieOverviewViewModel @Inject constructor(
    private val getMovieOverviewUseCase: GetMovieOverviewUseCase
): ViewModel() {

    private val _movieOverview: MutableStateFlow<MovieOverview?> = MutableStateFlow(null)
    val movieOverview: Flow<MovieOverview?> = _movieOverview.asStateFlow()

    fun viewCreated(movieItem: Movie) {
       getMovieOverviewUseCase.getMovieOverview(movieItem).onEach {
           _movieOverview.value = it
       }
           .launchIn(viewModelScope)
    }
}