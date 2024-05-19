package com.md.testnews.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.md.testnews.R

@Composable
fun TestNewsToolbar(
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null,
    @DrawableRes backIcon: Int = R.drawable.ic_arrow_white,
    title: String = ""
) {
    Row(modifier = modifier) {
        ButtonContainer{
            onBackPressed?.let {
                IconButton(onClick = it) {
                    Icon(painter = painterResource(id = backIcon), contentDescription = "icon_back")
                }
            }
        }
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            textAlign = TextAlign.Center,
        )
        ButtonContainer()
    }
}

@Composable
private fun ButtonContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.size(40.dp), contentAlignment = Alignment.Center) {
        content.invoke()
    }
}