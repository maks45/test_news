package com.md.testnews.screen.news_list.model

data class NewsData(
    val id: Int?,
    val title: String,
    val sourceName: String,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val timestamp: Long
)
