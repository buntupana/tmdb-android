package com.buntupana.tmdb.core.ui.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.Dimens

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

@Preview(showBackground = true)
@Composable
private fun DivisorCirclePreview() {
    DivisorCircle()
}
