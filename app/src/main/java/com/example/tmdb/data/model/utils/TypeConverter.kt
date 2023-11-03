package com.example.tmdb.data.model.utils

import com.example.tmdb.data.model.Type

class TypeConverter {
    @androidx.room.TypeConverter
    fun fromType(type: Type): String {
        return type.name
    }

    @androidx.room.TypeConverter
    fun toType(typeName: String): Type {
        return Type.valueOf(typeName)
    }
}