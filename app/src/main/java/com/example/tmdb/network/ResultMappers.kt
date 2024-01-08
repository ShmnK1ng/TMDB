package com.example.tmdb.network

fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

fun <T> Result<T>.asSuccess(): Result.Success<T> {
    return this as Result.Success<T>
}

fun <T> Result<T>.asFailure(): Result.Failure {
    return this as Result.Failure
}