package com.buntupana.tmdb.core.ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun VerticalTextRoulette(
    modifier: Modifier = Modifier,
    text: String?,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight = FontWeight.Normal,
    style: TextStyle = LocalTextStyle.current
) {

    text ?: return

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
            fontSize = fontSize,
            fontWeight = fontWeight,
            style = style
        )
    }
}