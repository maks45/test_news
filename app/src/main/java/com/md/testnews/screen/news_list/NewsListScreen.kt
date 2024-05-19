package com.md.testnews.screen.news_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.md.testnews.R
import com.md.testnews.ui.component.TestNewsToolbar
import com.md.testnews.screen.news_list.model.NewsData
import com.md.testnews.screen.news_list.model.NewsListScreenState
import com.md.testnews.ui.component.ErrorScreen
import com.md.testnews.ui.component.LoadingScreen
import com.md.testnews.ui.theme.TestNewsTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun NewsListScreen(viewModel: NewsListViewModel = getViewModel(), onItemSelected: (Int) -> Unit) {
    val screenState = viewModel.newsScreenState.collectAsState()
    //this approach was added for possibility to load news with different sources
    val firstVisibleIndexState = remember { mutableIntStateOf(-1) }
    val titleState = remember(screenState.value) {
        derivedStateOf {
            when (val state = screenState.value) {
                is NewsListScreenState.Data -> {
                    state.data.getOrNull(firstVisibleIndexState.intValue)?.sourceName
                        ?: ""
                }

                else -> ""
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        TestNewsToolbar(title = titleState.value)
        NewsScreenContent(
            state = screenState.value,
            onItemSelected = onItemSelected,
            firstVisibleIndexState = firstVisibleIndexState
        )
    }
}

@Composable
fun NewsScreenContent(
    state: NewsListScreenState,
    onItemSelected: (Int) -> Unit,
    firstVisibleIndexState: MutableState<Int>
) {
    when (state) {
        is NewsListScreenState.Initial -> LoadingScreen()
        is NewsListScreenState.Data -> NewsList(
            items = state.data,
            onItemSelected = onItemSelected,
            firstVisibleIndexState = firstVisibleIndexState
        )

        is NewsListScreenState.Error -> ErrorScreen(errorMsg = state.message)
    }
}

@Composable
private fun NewsList(
    modifier: Modifier = Modifier,
    items: List<NewsData>,
    firstVisibleIndexState: MutableState<Int>,
    onItemSelected: (Int) -> Unit
) {
    val listState = remember { LazyListState() }
    val position = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    LaunchedEffect(key1 = position.value) {
        firstVisibleIndexState.value = position.value
    }
    LazyColumn(modifier = modifier.fillMaxSize(), state = listState) {
        itemsIndexed(items) { index, newsData ->
            NewsDataItem(
                modifier = Modifier.clickable {
                    newsData.id?.let {
                        onItemSelected(it)
                    }
                },
                item = newsData
            )
            if (index == items.lastIndex) {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun NewsDataItem(modifier: Modifier = Modifier, item: NewsData) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Column {
            item.urlToImage?.let {
                val context = LocalContext.current
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    model = it,
                    contentDescription = "news_item_image",
                    contentScale = ContentScale.FillWidth,
                    imageLoader = ImageLoader(context)
                )
            }
            Text(
                modifier = Modifier
                    .padding(all = 16.dp),
                text = item.title
            )
        }
    }
}
