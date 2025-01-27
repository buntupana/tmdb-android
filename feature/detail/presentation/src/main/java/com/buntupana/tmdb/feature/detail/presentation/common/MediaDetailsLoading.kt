package com.buntupana.tmdb.feature.detail.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun MediaDetailsLoading(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    delayTime: Long = 700
) {

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(showLoading.not()) {
        delay(delayTime)
        showLoading = true
    }

    Box(
        modifier = modifier
            .background(backgroundColor)
    ) {
        if (showLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = backgroundColor.getOnBackgroundColor()
            )
        }
    }
}

@Preview
@Composable
fun MediaDetailsLoadingPreview() {

    MediaDetailsLoading(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        backgroundColor = Color.Black
    )
}