package com.md.testnews.screen.news_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.md.testnews.screen.news_item.model.NewsItemScreenState
import com.md.testnews.screen.news_list.model.NewsData
import com.md.testnews.ui.component.ErrorScreen
import com.md.testnews.ui.component.LoadingScreen
import com.md.testnews.ui.component.TestNewsToolbar
import org.koin.androidx.compose.getViewModel

const val ARG_NEWS_ITEM_SCREEN_ID = "arg.news.item.id"

@Composable
fun NewsItemScreen(
    viewModel: NewsItemViewModel = getViewModel(),
    itemId: Int,
    onBack: () -> Unit
) {
    val screenState = viewModel.newsItemScreenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadNewsById(itemId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        TestNewsToolbar(onBackPressed = onBack)
        NewsItemScreenContent(state = screenState.value)
    }
}

@Composable
private fun NewsItemScreenContent(state: NewsItemScreenState) {
    when (state) {
        is NewsItemScreenState.Data -> NewsItemContent(newsItem = state.data)
        is NewsItemScreenState.Error -> ErrorScreen()
        is NewsItemScreenState.Initial -> LoadingScreen()
    }
}

@Composable
private fun NewsItemContent(
    modifier: Modifier = Modifier,
    newsItem: NewsData
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        newsItem.urlToImage?.let {
            val context = LocalContext.current
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                model = it,
                contentDescription = "news_item_image",
                contentScale = ContentScale.FillWidth,
                imageLoader = ImageLoader(context)
            )
        }
        Text(
            modifier = Modifier.padding(all = 16.dp),
            text = newsItem.title
        )
        newsItem.description?.let {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = it
            )
        }
        newsItem.content?.let {
            Text(
                modifier = Modifier.padding(all = 16.dp),
                text = it
            )
        }
    }
}
