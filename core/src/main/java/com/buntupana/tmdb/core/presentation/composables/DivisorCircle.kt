package com.buntupana.tmdb.core.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DivisorCircle(
    size: Dp = 4.dp,
    padding: Dp = 4.dp,
    color: Color = Color.Black
) {
    Canvas(
        modifier = Modifier
            .padding(padding)
            .size(size),
        onDraw = {
            drawCircle(color = color)
        })
}
