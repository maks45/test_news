package com.md.testnews.screen.main

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.md.testnews.screen.login.LoginScreen
import com.md.testnews.screen.main.navigation.Screen
import com.md.testnews.screen.news_item.ARG_NEWS_ITEM_SCREEN_ID
import com.md.testnews.screen.news_item.NewsItemScreen
import com.md.testnews.screen.news_list.NewsListScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.statusBarsPadding(),
        navController = navController,
        startDestination = Screen.NewsLogin.name
    ) {
        composable(route = Screen.NewsLogin.name) {
            LoginScreen(onLogin = {
                navController.navigate(
                    route = Screen.NewsList.name
                )
            })
        }
        composable(route = Screen.NewsList.name) {
            val activity = LocalContext.current as? Activity
            BackHandler(true) {
                activity?.finish()
            }
            NewsListScreen(
                onItemSelected = {
                    navController.navigate(
                        route = Screen.NewsItem.name.replace(
                            "{$ARG_NEWS_ITEM_SCREEN_ID}",
                            it.toString()
                        ),
                    )
                }
            )
        }
        composable(route = Screen.NewsItem.name) {
            //this is compose issue can't get int value from route, see issue tracker
            val itemId = it.arguments?.getString(ARG_NEWS_ITEM_SCREEN_ID)?.toInt()
            itemId?.let { id ->
                NewsItemScreen(
                    itemId = id,
                    onBack = navController::popBackStack
                )
            }
        }
    }
}