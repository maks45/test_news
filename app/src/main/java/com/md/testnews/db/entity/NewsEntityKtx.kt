package com.md.testnews.db.entity

import com.md.testnews.screen.news_list.model.NewsData

fun NewsEntity.toNewsData(): NewsData {
    return NewsData(
        id = id,
        title = title,
        sourceName = sourceName ?: "",
        content = content,
        description = description,
        urlToImage = urlToImage,
        timestamp = timestamp
    )
}