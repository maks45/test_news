package com.md.testnews.screen.news_list.model

sealed interface NewsListScreenState {
    data object Initial : NewsListScreenState
    class Data(val data: List<NewsData>) : NewsListScreenState
    class Error(val message: String) : NewsListScreenState
}