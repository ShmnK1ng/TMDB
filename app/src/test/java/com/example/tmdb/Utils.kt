package com.example.tmdb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectInViewModelScope(
    viewModel: ViewModel, block: (() -> Unit)? = null
): List<T> {
    val result: MutableList<T> = mutableListOf()
    val job = viewModel.viewModelScope.launch {
        this@collectInViewModelScope.collect {
            result.add(it)
        }
    }
    block?.invoke()
    job.cancel()
    return result
}