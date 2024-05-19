package com.md.testnews.domain

import android.util.Log
import com.md.testnews.api.NewsApi
import com.md.testnews.db.NewsDao
import com.md.testnews.db.entity.toNewsData
import com.md.testnews.core.network.NetworkStateProvider
import com.md.testnews.model.toEntity
import com.md.testnews.screen.news_list.model.NewsData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NewsRepository(
    private val api: NewsApi,
    private val newsDao: NewsDao,
    private val networkStateProvider: NetworkStateProvider

) {
    private val _cachedNewsFlow = MutableSharedFlow<List<NewsData>>(replay = 1)
    val cachedNewsFlow = _cachedNewsFlow.asSharedFlow()

    suspend fun updateNews() {
        if (networkStateProvider.isOnline()) {
            fetchNewsFromServer()
        } else {
            loadCachedNews()
        }
    }

    suspend fun getNewsItemById(id: Int): NewsData {
        newsDao.getById(id)?.toNewsData()?.let {
            return it
        } ?: throw IllegalStateException("News with given id wasn't found")
    }

    private suspend fun fetchNewsFromServer() {
        try {
            val newsList = api.getAllNews().articles
            newsDao.clear()
            newsList.onEach {
                newsDao.addNews(it.toEntity())
            }
            val updatedCache = newsDao.getAllSortByDate()
            _cachedNewsFlow.emit(updatedCache.map { it.toNewsData() })
        } catch (e: Exception) {
            loadCachedNews()
            throw e
        }
    }

    private suspend fun loadCachedNews() {
        val cachedNews = newsDao.getAllSortByDate()
        _cachedNewsFlow.emit(cachedNews.map { it.toNewsData() })
    }

}