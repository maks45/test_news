package com.md.testnews.di

import com.md.testnews.BuildConfig
import com.md.testnews.core.rest.ApiKeyInterceptor
import com.md.testnews.core.rest.LogInterceptor
import com.md.testnews.core.rest.SourceInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.sin

val koinRestModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .addInterceptor(get<LogInterceptor>())
            .addInterceptor(get<SourceInterceptor>())
            .build()
    }

    single<ApiKeyInterceptor> {
        ApiKeyInterceptor()
    }

    single<SourceInterceptor> {
        SourceInterceptor()
    }

    single<LogInterceptor> {
        LogInterceptor()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

}