package com.md.testnews.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.md.testnews.db.entity.NewsEntity

@Dao
interface NewsDao {
    @Upsert
    suspend fun addNews(newsEntity: NewsEntity): Long

    @Query("DELETE FROM news")
    suspend fun clear()

    @Query("SELECT * FROM news WHERE id =:id")
    suspend fun getById(id: Int): NewsEntity?

    @Query("SELECT * FROM news ORDER BY timestamp DESC")
    suspend fun getAllSortByDate(): List<NewsEntity>

}
