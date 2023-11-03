package com.example.tmdb.data.model.utils

import java.time.Instant

class InstantConverter {
    @androidx.room.TypeConverter
    fun fromInstant(instant: Instant): String {
        return instant.toString()
    }

    @androidx.room.TypeConverter
    fun toInstant(value: String): Instant {
        return Instant.parse(value)
    }
}