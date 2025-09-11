package com.buntupana.tmdb.core.ui.composables

import android.content.res.Configuration
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
import com.buntupana.tmdb.core.ui.theme.AppTheme
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
private fun CircularProgressIndicatorDelayedPreview() {
    AppTheme {
        CircularProgressIndicatorDelayed(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}