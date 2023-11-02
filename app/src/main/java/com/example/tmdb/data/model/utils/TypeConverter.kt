package com.example.tmdb.data.model.utils

import com.example.tmdb.data.model.Type

class TypeConverter {
    @androidx.room.TypeConverter
    fun fromType(type: Type): String {
        return when (type) {
            Type.Movies -> Type.Movies.name
            Type.Series -> Type.Series.name
        }
    }

    @androidx.room.TypeConverter
    fun toType(type: String): Type {
        return when (type) {
            Type.Movies.name -> Type.Movies
            Type.Series.name -> Type.Series
            else -> throw IllegalArgumentException("Invalid value for Type: $type")
        }
    }
}