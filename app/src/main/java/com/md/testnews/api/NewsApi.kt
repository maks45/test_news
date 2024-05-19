package com.md.testnews.api

import com.md.testnews.model.NewsResponse
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines")
    suspend fun getAllNews(): NewsResponse

}