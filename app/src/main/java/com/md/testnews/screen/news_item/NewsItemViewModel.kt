package com.md.testnews.screen.news_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.md.testnews.ktx.defaultErrorMessage
import com.md.testnews.ktx.exceptionHandler
import com.md.testnews.screen.news_item.model.NewsItemScreenState
import com.md.testnews.domain.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsItemViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _newsItemScreenState =
        MutableStateFlow<NewsItemScreenState>(NewsItemScreenState.Initial)
    val newsItemScreenState = _newsItemScreenState.asStateFlow()

    fun loadNewsById(id: Int) {
        viewModelScope.launch(exceptionHandler {
            _newsItemScreenState.value =
                NewsItemScreenState.Error(it.message ?: defaultErrorMessage)
        }) {
            val item = repository.getNewsItemById(id)
            _newsItemScreenState.value = NewsItemScreenState.Data(data = item)
        }
    }

}