package com.example.tmdb.di

import android.content.Context
import com.example.tmdb.data.model.BASE_URL
import com.example.tmdb.data.model.RequestsInterceptor
import com.example.tmdb.data.model.utils.InstantAdapter
import com.example.tmdb.network.ResultAdapterFactory
import com.example.tmdb.ui.home.utils.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(RequestsInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInterface(
        OkHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(ResultAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideMoshi(@ApplicationContext context: Context): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(InstantAdapter(context))
        .build()
}