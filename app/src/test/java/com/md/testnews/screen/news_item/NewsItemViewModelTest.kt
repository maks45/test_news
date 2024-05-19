package com.md.testnews.screen.news_item

import android.util.Log
import com.md.testnews.domain.NewsRepository
import com.md.testnews.rule.MainDispatcherRule
import com.md.testnews.screen.news_item.model.NewsItemScreenState
import com.md.testnews.screen.news_list.model.NewsData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsItemViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())
    private val newsDataFlow = MutableSharedFlow<List<NewsData>>()
    private val repository = mockk<NewsRepository>(relaxed = true) {
        every { cachedNewsFlow } returns newsDataFlow.asSharedFlow()
        coEvery { getNewsItemById(any()) } returns mockk<NewsData>(relaxed = true)
    }
    private lateinit var viewModel: NewsItemViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        viewModel = NewsItemViewModel(
            repository = repository
        )
    }

    @Test
    fun `loadNewsById ok`() = runTest {
        assert(viewModel.newsItemScreenState.value is NewsItemScreenState.Initial)
        viewModel.loadNewsById(123)
        coVerify { repository.getNewsItemById(any()) }
        newsDataFlow.emit(listOf(mockk(), mockk()))
        assert(viewModel.newsItemScreenState.value is NewsItemScreenState.Data)
    }

    @Test
    fun `loadNewsById error`() = runTest {
        coEvery { repository.getNewsItemById(any()) } throws Exception()
        assert(viewModel.newsItemScreenState.value is NewsItemScreenState.Initial)
        viewModel.loadNewsById(123)
        coVerify { repository.getNewsItemById(any()) }
        assert(viewModel.newsItemScreenState.value is NewsItemScreenState.Error)
    }

}
