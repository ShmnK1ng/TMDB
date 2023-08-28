package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: Flow<List<Category>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            getCategoriesUseCase.getCategories()
                .onEach { categories ->
                        _categories.value = categories
                }
                .launchIn(this)
        }
    }
}