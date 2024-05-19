package com.md.testnews.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("totalResults") val totalResults: Int,
    @SerialName("articles") val articles: List<NewsItem>
)