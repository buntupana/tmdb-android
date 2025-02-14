package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.background
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun CircularProgressIndicatorDelayed(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    delayTime: Long = 500
) {

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(showLoading.not()) {
        delay(delayTime)
        showLoading = true
    }


    if (showLoading) {
        CircularProgressIndicator(
            modifier = modifier,
            color = color
        )
    }
}

@Preview
@Composable
private fun CircularProgressIndicatorDelayedPreview() {

    CircularProgressIndicatorDelayed(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    )
}