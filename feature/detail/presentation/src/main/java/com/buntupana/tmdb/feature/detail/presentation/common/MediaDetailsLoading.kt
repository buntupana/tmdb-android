package com.buntupana.tmdb.feature.detail.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun MediaDetailsLoading(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    Column(
        modifier = modifier
            .padding(vertical = 200.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            color = backgroundColor.getOnBackgroundColor()
        )
    }
}

@Preview
@Composable
fun MediaDetailsLoadingPreview() {

    MediaDetailsLoading(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        backgroundColor = MaterialTheme.colorScheme.background
    )
}