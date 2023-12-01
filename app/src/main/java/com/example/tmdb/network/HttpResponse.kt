package com.example.tmdb.network

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}