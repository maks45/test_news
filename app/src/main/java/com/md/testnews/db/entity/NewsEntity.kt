package com.md.testnews.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String?,
    val sourceName: String?,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val content: String?,
    val timestamp: Long
)
