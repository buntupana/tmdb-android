package com.buntupana.tmdb.core.ui.composables.widget

import android.content.res.Configuration
import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.HkFontFamily
import com.buntupana.tmdb.core.ui.theme.StaticColor
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.core.ui.util.toSp

@Composable
fun UserScore(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    @IntRange(from = 0, to = 100) score: Int?,
    backGroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    noScoreColor: Color = StaticColor.scoreNoScore,
    lowScoreColor: Color = StaticColor.scoreLowScore,
    mediumScoreColor: Color = StaticColor.scoreMediumScore,
    highScoreColor: Color = StaticColor.scoreHighScore,
    fontFamily: FontFamily = HkFontFamily
) {

    val contentColor = backGroundColor.getOnBackgroundColor()

    // Choosing stroke color depending on score
    val strokeColor = when (score) {
        in 0..39 -> lowScoreColor
        in 40..69 -> mediumScoreColor
        in 70..100 -> highScoreColor
        else -> noScoreColor
    }

    BoxWithConstraints(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backGroundColor),
        contentAlignment = Alignment.Center
    ) {

        val dimensionRef = if (maxHeight > maxWidth) maxWidth else maxHeight

        // Calc dimension value for stroke
        val strokeSize = dimensionRef * 5f / 100f

        Canvas(modifier = Modifier.fillMaxSize(0.82f)) {
            val backgroundAlpha = if (score == null) 1f else 0.3f
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                alpha = backgroundAlpha,
                style = Stroke(strokeSize.toPx())
            )
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = (score ?: 0) * 360f / 100f,
                useCenter = false,
                style = Stroke(strokeSize.toPx(), cap = StrokeCap.Round)
            )
        }

        // Calc dimension values for texts
        val textPadding = dimensionRef * 17f / 100f
        val textSize = toSp(dp = dimensionRef * 34f / 100f)
        val symbolSize = toSp(dp = dimensionRef * 10f / 100f)

        Row(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(textPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (score == null) {
                Text(
                    text = "NR",
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontSize = textSize,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
            } else {
                Text(
                    text = score.toString(),
                    color = contentColor,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    fontSize = textSize,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
                Text(
                    text = "%",
                    color = contentColor,
                    fontFamily = fontFamily,
                    modifier = Modifier.fillMaxHeight(0.5f),
                    fontSize = symbolSize,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = true
                        )
                    )
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun UserScorePreview() {
    AppTheme {
        UserScore(
            size = 100.dp,
            score = 20
        )
    }
}