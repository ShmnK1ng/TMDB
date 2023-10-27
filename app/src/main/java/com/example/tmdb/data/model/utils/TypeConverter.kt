package com.example.tmdb.data.model.utils

import com.example.tmdb.data.model.MOVIES_TYPE
import com.example.tmdb.data.model.SERIES_TYPE
import com.example.tmdb.data.model.Type

class TypeConverter {
    @androidx.room.TypeConverter
    fun fromType(type: Type): Int {
        return when (type) {
            Type.Movies -> MOVIES_TYPE
            Type.Series -> SERIES_TYPE
        }
    }

    @androidx.room.TypeConverter
    fun toType(typeInt: Int): Type {
        return when (typeInt) {
            MOVIES_TYPE -> Type.Movies
            SERIES_TYPE -> Type.Series
            else -> throw IllegalArgumentException("Invalid value for Type: $typeInt")
        }
    }
}