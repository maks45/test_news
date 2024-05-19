package com.md.testnews.screen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.fragment.app.FragmentActivity
import com.md.testnews.screen.main.MainScreen
import com.md.testnews.screen.news_list.NewsListScreen
import com.md.testnews.ui.theme.TestNewsTheme
import org.koin.compose.KoinContext

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                TestNewsTheme {
                    Surface {
                        MainScreen()
                    }
                }
            }
        }
    }
}