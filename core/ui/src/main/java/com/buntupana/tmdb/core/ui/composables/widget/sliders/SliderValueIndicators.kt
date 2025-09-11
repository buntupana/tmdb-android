package com.buntupana.tmdb.core.ui.composables.widget.sliders

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.AppTheme

@Composable
fun SliderValueIndicators(
    modifier: Modifier = Modifier,
    indicatorCount: Int
) {
    Row(
        modifier = modifier.fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(indicatorCount) {
            Box(
                Modifier.weight(1f)
            ) {
                if (it != 0) {
                    Box(
                        Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .size(2.dp)
                    )
                }
            }
        }
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
private fun SliderValueIndicatorsPreview() {
    AppTheme {
        SliderValueIndicators(
            indicatorCount = 10
        )
    }
}