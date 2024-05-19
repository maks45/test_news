package com.md.testnews.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    @SerialName("author") val author: String,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String?,
    @SerialName("urlToImage") val urlToImage: String?,
    @SerialName("content") val content: String?,
    @SerialName("publishedAt") val publishedAt: String,
    @SerialName("source") val source: NewsSource
)
