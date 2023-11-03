package com.example.tmdb.ui.home.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

fun <T> Flow<T>.flowWithStartedLifecycle(owner: LifecycleOwner): Flow<T> {
    return flowWithLifecycle(owner.lifecycle, Lifecycle.State.STARTED)
}