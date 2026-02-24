package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified,
    fadeColor: Color = MaterialTheme.colorScheme.background,
    fontSize: TextUnit = TextUnit.Unspecified,
    expandColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    fontWeight: FontWeight = FontWeight.Normal,
    isExpanded: Boolean = false,
    collapsedVisibleLines: Int = 12
) {

    var textExpanded by remember {
        mutableStateOf(isExpanded)
    }

    val extraHeight = if (textExpanded) 0.dp else 32.dp

    Box(
        modifier = modifier
    ) {

        val gradientMargin = 32.dp
        var buttonHeight by remember { mutableStateOf(0.dp) }

        Text(
            modifier = Modifier
                .padding(bottom = extraHeight)
                .animateContentSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {

                    drawContent()
                    if (textExpanded) return@drawWithContent

                    val gradient = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        ),
                        startY = size.height - (buttonHeight + gradientMargin).toPx(),
                        endY = size.height
                    )

                    drawRect(
                        brush = gradient,
                        blendMode = BlendMode.DstIn
                    )
                },
            text = text,
            color = color,
            fontSize = fontSize,
            fontWeight = fontWeight,
            maxLines = if (textExpanded) Int.MAX_VALUE else collapsedVisibleLines,
            onTextLayout = {
                if (!textExpanded) {
                    textExpanded = it.lineCount < collapsedVisibleLines && textExpanded == false
                }
            }
        )

        if (textExpanded) return@Box

        Box(
            Modifier
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.BottomEnd
        ) {

            val density = LocalDensity.current

            AppTextButton(
                modifier = Modifier
                    .onGloballyPositioned {
                        with(density) {
                            buttonHeight = it.size.height.toDp()
                        }
                    },
                onClick = { textExpanded = true },
                rippleColor = fadeColor.getOnBackgroundColor(),
            ) {
                Text(
                    text = stringResource(id = R.string.common_read_more),
                    color = expandColor,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(expandColor)
                )
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
private fun ExpandableTextPreview() {
    AppTheme {
        ExpandableText(
            text = """
            111111111111111111111111111111111111111111111111111
            222222222222222222222222222222222222222222222222222
            333333333333333333333333333333333333333333333333333
            44444444444444444444444444444444444444444
            555555555555555555555555555555555555555555555555555
            666666666666666666666666666666666666666666666666666
            777777777777777777777777777777777777777777777777777
            888888888888888888888888888888888888888888888888888
            999999999999999999999999999999999999999999999999999
            """.trimIndent(),
            collapsedVisibleLines = 8
        )
    }
}