package com.example.tmdb.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    val id: Int = 0,
    val remoteId: String,
    val title: String,
    val rating: Double,
    val posterPath: String,
    val type: Type
) : Parcelable