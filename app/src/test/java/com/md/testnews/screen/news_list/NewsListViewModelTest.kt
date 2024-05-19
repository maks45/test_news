package com.md.testnews.screen.news_list

import android.util.Log
import com.md.testnews.domain.NewsRepository
import com.md.testnews.rule.MainDispatcherRule
import com.md.testnews.screen.news_item.NewsItemViewModel
import com.md.testnews.screen.news_item.model.NewsItemScreenState
import com.md.testnews.screen.news_list.model.NewsData
import com.md.testnews.screen.news_list.model.NewsListScreenState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(UnconfinedTestDispatcher())
    private val newsDataFlow = MutableSharedFlow<List<NewsData>>()
    private val repository = mockk<NewsRepository>(relaxed = true) {
        every { cachedNewsFlow } returns newsDataFlow
    }
    private lateinit var viewModel: NewsListViewModel

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        viewModel = NewsListViewModel(
            repository = repository
        )
    }

    @Test
    fun `updateNews ok`() = runTest {
        assert(viewModel.newsScreenState.value is NewsListScreenState.Initial)
        newsDataFlow.emit(listOf(mockk(), mockk()))
        assert(viewModel.newsScreenState.value is NewsListScreenState.Data)
    }

    @Test
    fun `updateNews error`() {
        coEvery { repository.updateNews() } throws Exception()
        coVerify { repository.updateNews() }
        assert(viewModel.newsScreenState.value is NewsListScreenState.Initial)
        viewModel = NewsListViewModel(repository = repository)
        coVerify(exactly = 2) { repository.updateNews() }
        assert(viewModel.newsScreenState.value is NewsListScreenState.Error)
    }

}
