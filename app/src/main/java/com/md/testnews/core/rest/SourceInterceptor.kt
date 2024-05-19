package com.md.testnews.core.rest

import com.md.testnews.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class SourceInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url
                        .newBuilder()
                        .addQueryParameter("sources", BuildConfig.NEWS_SOURCE)
                        .build()
                ).build()
        )
    }

}