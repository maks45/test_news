package com.md.testnews.domain

import com.md.testnews.api.NewsApi
import com.md.testnews.core.network.NetworkStateProvider
import com.md.testnews.db.NewsDao
import com.md.testnews.db.entity.NewsEntity
import com.md.testnews.model.NewsItem
import com.md.testnews.model.NewsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NewsRepositoryTest {
    private val news = mockk<NewsItem>(relaxed = true) {
        every { publishedAt } returns "2024-05-19T15:59:53Z"
    }
    private val newsResponse = mockk<NewsResponse>(relaxed = true) {
        every { articles } returns listOf(news)
    }
    private val api = mockk<NewsApi> {
        coEvery { getAllNews() } returns newsResponse
    }
    private val networkStateProvider = mockk<NetworkStateProvider>()
    private val newsEntity = mockk<NewsEntity>(relaxed = true)
    private val newsDao = mockk<NewsDao>(relaxed = true) {
        coEvery { getById(any()) } returns newsEntity
        coEvery { getAllSortByDate() } returns listOf(newsEntity)
    }
    private val repository = NewsRepository(
        api = api,
        networkStateProvider = networkStateProvider,
        newsDao = newsDao
    )

    @Test
    fun `updateNews offline`() {
        every { networkStateProvider.isOnline() } returns false
        runBlocking {
            repository.updateNews()
            coVerify { newsDao.getAllSortByDate() }
        }
    }

    @Test
    fun `updateNews online`() {
        every { networkStateProvider.isOnline() } returns true
        runBlocking {
            repository.updateNews()
            coVerify { api.getAllNews() }
            coVerify { newsDao.clear() }
            verify { newsResponse.articles }
            coVerify { newsDao.addNews(any()) }
            coVerify { newsDao.getAllSortByDate() }
        }
    }

    @Test
    fun `updateNews online error`() {
        every { networkStateProvider.isOnline() } returns true
        val error = Error()
        coEvery { api.getAllNews() } throws error
        runBlocking {
            try {
                repository.updateNews()
                coVerify(exactly = 1) { api.getAllNews() }
                coVerify(exactly = 0) { newsDao.clear() }
                verify(exactly = 0) { newsResponse.articles }
                coVerify(exactly = 0) { newsDao.addNews(any()) }
                coVerify(exactly = 0) { newsDao.getAllSortByDate() }
            } catch (e: Throwable) {
                assert(e == error)
            }
        }
    }

}