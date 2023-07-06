package com.example.tmdb.data.model

import okhttp3.Interceptor

class RequestsInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response = chain.run {
        proceed(
            request().newBuilder()
                .url(
                    request().url.newBuilder()
                        .addQueryParameter(
                            QUERY_PARAMETER_NAME_LANGUAGE,
                            QUERY_PARAMETER_LANGUAGE_VALUE
                        )
                        .build()
                )
                .addHeader(HEADER_NAME_AUTHORIZATION, HEADER_AUTHORIZATION_VALUE)
                .build()
        )
    }
}