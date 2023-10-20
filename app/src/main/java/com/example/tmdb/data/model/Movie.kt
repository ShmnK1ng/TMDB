package com.example.tmdb.data.model

data class Movie (
    val id: Int = 0,
    val remoteId: String,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val type: Int
) : java.io.Serializable