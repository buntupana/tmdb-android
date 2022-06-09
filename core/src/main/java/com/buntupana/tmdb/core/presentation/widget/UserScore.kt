package com.buntupana.tmdb.core.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.theme.PrimaryDark

private const val MAX_VALUE = 99
private const val MIN_VALUE = 0

@Composable
fun UserScore(
    modifier: Modifier = Modifier,
    score: Int = 50,
    backGroundColor: Color = PrimaryDark,
    noScoreColor: Color = Color(0xFF666666),
    lowScoreColor: Color = Color(0xFFDB2360),
    mediumScoreColor: Color = Color(0xFFD2D531),
    highScoreColor: Color = Color(0xFF21D07A),
    fontFamily: FontFamily = FontFamily.Default
) {

    // checking that score is between valid values
    val correctedScore = when {
        score > MAX_VALUE -> MAX_VALUE
        score < MIN_VALUE -> MIN_VALUE
        else -> score
    }

    // Choosing stroke color depending on score
    val strokeColor = when (correctedScore) {
        0 -> noScoreColor
        in 1..39 -> lowScoreColor
        in 40..69 -> mediumScoreColor
        else -> highScoreColor
    }

    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(backGroundColor),
        contentAlignment = Alignment.Center
    ) {

        val dimensionRef = if (maxHeight > maxWidth) maxWidth else maxHeight

        // Calc dimension value for stroke
        val strokeSize = dimensionRef * 5f / 100f

        Canvas(modifier = Modifier.fillMaxSize(0.82f)) {
            val backgroundAlpha = if(correctedScore < 1) 1f else 0.3f
            drawArc(
                color = strokeColor,
                -90f,
                360f,
                useCenter = false,
                alpha = backgroundAlpha,
                style = Stroke(strokeSize.toPx())
            )
            drawArc(
                color = strokeColor,
                -90f,
                correctedScore * 360f / 100f,
                useCenter = false,
                style = Stroke(strokeSize.toPx(), cap = StrokeCap.Round)
            )
        }

        // Calc dimension values for texts
        val textPadding = dimensionRef * 17f / 100f
        val textSize = dpToSp(dp = dimensionRef * 34f / 100f)
        val symbolSize = dpToSp(dp = dimensionRef * 10f / 100f)

        Row(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(textPadding),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (correctedScore < 1) {
                Text(
                    text = "NR",
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontFamily = fontFamily,
                    fontSize = textSize
                )
            } else {
                Text(
                    text = correctedScore.toString(),
                    color = Color.White,
                    textAlign = TextAlign.End,
                    fontFamily = fontFamily,
                    fontSize = textSize
                )
                Text(
                    text = "%",
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    fontFamily = fontFamily,
                    modifier = Modifier.fillMaxHeight(0.455f),
                    fontSize = symbolSize
                )
            }
        }
    }
}

@Composable
@Preview
fun UserScorePreview() {
    UserScore(Modifier.size(width = 100.dp, height = 100.dp), score = 0)
}