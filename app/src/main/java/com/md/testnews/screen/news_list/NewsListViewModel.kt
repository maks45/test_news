package com.md.testnews.screen.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.testnews.ktx.defaultErrorMessage
import com.md.testnews.ktx.exceptionHandler
import com.md.testnews.domain.NewsRepository
import com.md.testnews.screen.news_list.model.NewsListScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NewsListViewModel(
    private val repository: NewsRepository
) : ViewModel() {
    private val _newsListScreenState = MutableStateFlow<NewsListScreenState>(NewsListScreenState.Initial)
    val newsScreenState = _newsListScreenState.asStateFlow()

    init {
        repository.cachedNewsFlow.onEach {
            _newsListScreenState.value = NewsListScreenState.Data(data = it)
        }.launchIn(viewModelScope)

       refreshFeed()
    }

    private fun refreshFeed(){
        viewModelScope.launch(
            exceptionHandler {
                _newsListScreenState.value =
                    NewsListScreenState.Error(it.localizedMessage ?: defaultErrorMessage)
            }
        ) {
            repository.updateNews()
        }
    }
}
