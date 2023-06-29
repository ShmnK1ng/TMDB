package com.example.tmdb.data.model

import com.example.tmdb.ui.home.utils.HEADER_AUTORIZATION_VALUE
import com.example.tmdb.ui.home.utils.HEADER_NAME_AUTHORIZATION
import com.example.tmdb.ui.home.utils.QUERY_PARAMETER_LANGUAGE_VALUE
import com.example.tmdb.ui.home.utils.QUERY_PARAMETER_NAME_LANGUAGE
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
                .addHeader(HEADER_NAME_AUTHORIZATION, HEADER_AUTORIZATION_VALUE)
                .build()
        )
    }
}