package com.buntupana.tmdb.core.presentation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.theme.Dimens

@Composable
fun DivisorCircle(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Canvas(
        modifier = modifier
            .padding(Dimens.padding.tiny)
            .size(4.dp),
        onDraw = {
            drawCircle(color = color)
        })
}
