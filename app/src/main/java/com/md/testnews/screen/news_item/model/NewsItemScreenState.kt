package com.md.testnews.screen.news_item.model

import com.md.testnews.screen.news_list.model.NewsData

sealed interface NewsItemScreenState {
    data object Initial : NewsItemScreenState
    data class Data(val data: NewsData) : NewsItemScreenState
    data class Error(val message: String) : NewsItemScreenState
}