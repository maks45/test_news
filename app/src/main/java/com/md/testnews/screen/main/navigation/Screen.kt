package com.md.testnews.screen.main.navigation

import com.md.testnews.screen.news_item.ARG_NEWS_ITEM_SCREEN_ID

sealed class Screen(val name: String) {
    data object NewsLogin: Screen("newsLogin")
    data object NewsList : Screen("newsList")
    data object NewsItem : Screen("newsItem/{$ARG_NEWS_ITEM_SCREEN_ID}")
}
