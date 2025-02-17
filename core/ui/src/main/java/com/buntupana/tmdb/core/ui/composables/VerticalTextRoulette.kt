package com.buntupana.tmdb.core.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun VerticalTextRoulette(
    modifier: Modifier = Modifier,
    text: String?,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal
) {

    text ?: return

    var previousText by remember { mutableStateOf(text) }

    AnimatedContent(
        targetState = text,
        transitionSpec = {
            if (targetState > initialState) {
                // Number increased → Animate up
                slideInVertically { it } + fadeIn() togetherWith slideOutVertically { -it } + fadeOut()
            } else {
                // Number decreased → Animate down
                slideInVertically { -it } + fadeIn() togetherWith slideOutVertically { it } + fadeOut()
            }
        },
        label = "NumberAnimation"
    ) { targetText ->
        Text(
            modifier = modifier,
            text = targetText,
            color = color,
            fontWeight = fontWeight
        )
    }

    LaunchedEffect(text) {
        previousText = text
    }
}