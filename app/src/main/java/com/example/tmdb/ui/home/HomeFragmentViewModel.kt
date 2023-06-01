package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    dataRepository: DataRepository
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    init {
        _categories.value = dataRepository.getCategories()
    }
}