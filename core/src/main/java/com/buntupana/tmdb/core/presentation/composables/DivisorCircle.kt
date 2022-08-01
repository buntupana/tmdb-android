package com.buntupana.tmdb.core.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DivisorCircle(
    size: Dp = 4.dp,
    padding: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Canvas(
        modifier = Modifier
            .padding(padding)
            .size(size),
        onDraw = {
            drawCircle(color = color)
        })
}
