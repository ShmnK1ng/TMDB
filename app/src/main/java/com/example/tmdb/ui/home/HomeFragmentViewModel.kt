package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.Category
import com.example.tmdb.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _categories = MutableSharedFlow<List<Category>>(1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val categories : SharedFlow<List<Category>> = _categories.asSharedFlow()

    init {
        viewModelScope.launch {
            _categories.emitAll(
                flow {
                    emit(dataRepository.getCategories())
                }
            )
        }
    }

    fun viewStarted() {
        setCategoriesList(dataRepository.getCategories())
    }

    private fun setCategoriesList(categories: List<Category>) {
        _categories.tryEmit(categories)
    }
}