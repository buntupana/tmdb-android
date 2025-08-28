package com.buntupana.tmdb.core.ui.composables.widget.sliders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.SliderThumbColor

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
                            .background(SliderThumbColor)
                            .size(2.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SliderValueIndicatorsPreview() {
    SliderValueIndicators(
        indicatorCount = 10
    )
}