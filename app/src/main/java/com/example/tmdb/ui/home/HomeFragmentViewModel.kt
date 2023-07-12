package com.example.tmdb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.data.model.Category
import com.example.tmdb.data.usecase.GetCategoriesUseCase
import com.example.tmdb.data.usecase.SetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    setCategoriesUseCase: SetCategoriesUseCase
) : ViewModel() {

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(listOf())
    val categories: Flow<List<Category>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            _categories.value = getCategoriesUseCase.getCategories()
        }
    }
}