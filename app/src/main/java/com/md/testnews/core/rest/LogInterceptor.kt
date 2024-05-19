package com.md.testnews.core.rest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        Log.i("request url ->", chain.request().url.toString())
        Log.i("request body ->", request.body.toString())
        Log.i("response code <-", response.code.toString())
        Log.i("response body <-", response.peekBody(2048).string())
        return response
    }
}