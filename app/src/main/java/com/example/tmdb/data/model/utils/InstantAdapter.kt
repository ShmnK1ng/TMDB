package com.example.tmdb.data.model.utils

import android.content.Context
import com.example.tmdb.R
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class InstantAdapter(private val context: Context) {
    @FromJson
    fun fromJson(jsonDate: String): Instant {
        val formatter = DateTimeFormatter.ofPattern(context.getString(R.string.data_time_formatter_pattern))
        val localDate = LocalDate.parse(jsonDate, formatter)
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC)
    }

    @ToJson
    fun toJson(instant: Instant): String {
        val localDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
        val formatter = DateTimeFormatter.ofPattern(context.getString(R.string.data_time_formatter_pattern))
        return formatter.format(localDate)
    }
}