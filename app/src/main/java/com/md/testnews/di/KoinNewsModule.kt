package com.md.testnews.di

import com.md.testnews.api.NewsApi
import com.md.testnews.screen.news_item.NewsItemViewModel
import com.md.testnews.screen.news_list.NewsListViewModel
import com.md.testnews.domain.NewsRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val koinNewsModule = module {

    viewModel {
        NewsListViewModel(
            repository = get()
        )
    }

    viewModel {
        NewsItemViewModel(
            repository = get()
        )
    }

    single {
        NewsRepository(
            api = get(),
            newsDao = get(),
            networkStateProvider = get()
        )
    }

    factory<NewsApi> {
        val retrofit: Retrofit = get()
        retrofit.create(NewsApi::class.java)
    }

}
