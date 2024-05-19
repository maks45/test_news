package com.md.testnews.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.md.testnews.ui.theme.TestNewsTheme

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, errorMsg: String = "something went wrong") {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = errorMsg,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}