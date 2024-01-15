package com.example.tmdb

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectForTest(
    coroutineScope: CoroutineScope, block: (() -> Unit)? = null
): List<T> {
    return buildList {
    val job = coroutineScope.launch {
        this@collectForTest.collect {
           add(it)
        }
    }
    block?.invoke()
    job.cancel()
    }
}