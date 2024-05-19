package com.md.testnews.model

import com.md.testnews.db.entity.NewsEntity
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun NewsItem.toEntity(): NewsEntity {
    val timestamp = LocalDateTime
        .parse(publishedAt, DateTimeFormatter.ISO_DATE_TIME)
        .toInstant(ZoneOffset.UTC)
        .toEpochMilli()
    return NewsEntity(
        author = author,
        content = content,
        sourceName = source.name,
        description = description,
        title = title,
        urlToImage = urlToImage,
        timestamp = timestamp
    )
}