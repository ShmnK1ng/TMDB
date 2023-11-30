package com.example.tmdb.network

sealed class Result<out T> {

    data class Success<T>(val value: T) : Result<T>()

    sealed class Failure<E : Throwable> : Result<Nothing>() {

        data object OtherError : Failure<Throwable>()

        data object NetworkError : Failure<HttpException>()
    }
}