package com.example.tmdb.network

sealed class Result<out T> {

    data class Success<T>(val value: T) : Result<T>()

    sealed class Failure<E : Throwable>(open val error: E? = null) : Result<Nothing>() {

        class OtherError : Failure<Throwable>()

        class NetworkError : Failure<HttpException>()
    }
}