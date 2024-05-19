package com.md.testnews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.md.testnews.db.entity.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    companion object {
        const val NAME = "news_db"
    }

    abstract fun getNewsDao(): NewsDao

}