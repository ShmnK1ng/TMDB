package com.example.tmdb.network

sealed class Result<out T> {

    data class Success<T>(val value: T) : Result<T>()

    sealed class Failure : Result<Nothing>() {

        object OtherError : Failure()

        object NetworkError : Failure()
    }
}