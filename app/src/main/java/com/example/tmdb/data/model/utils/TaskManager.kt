package com.example.tmdb.data.model.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskManager @Inject constructor(private val coroutineScope: CoroutineScope) {
    private var job: Job? = null

    fun startTask(task: suspend () -> Unit) {
        if (job?.isActive == false || job == null) {
            job?.cancel()
            job = coroutineScope.launch { task() }
        }
    }
}