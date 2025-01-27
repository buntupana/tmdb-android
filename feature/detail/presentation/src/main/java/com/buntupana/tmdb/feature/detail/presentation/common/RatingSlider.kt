package com.buntupana.tmdb.feature.detail.presentation.common

import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.SliderThumbColor
import com.buntupana.tmdb.feature.detail.presentation.getRatingColor

private const val MAX_VALUE = 100
private const val MAX_VALUE_SLIDER = 100f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingSlider(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(from = 0, to = MAX_VALUE.toLong()) value: Int,
    onValueChange: (Int) -> Unit
) {

    Slider(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        value = value.toFloat(),
        valueRange = 0f..MAX_VALUE_SLIDER,
        steps = 9,
        onValueChange = {
            onValueChange(it.toInt())
        },
        thumb = {
            Column(
                modifier = Modifier.height(120.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp), // Reduced vertical padding
                        text = value.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .background(SliderThumbColor)
                        .size(width = 40.dp, height = 30.dp)
                )

                Box(modifier = Modifier.weight(1f))
            }
        },
        track = { sliderState ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(sliderState.value / sliderState.valueRange.endInclusive)
                        .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    getRatingColor(value).copy(alpha = 0.3f),
                                    getRatingColor(value)
                                )
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(sliderState.value.toInt() / 10) {
                        Box(
                            Modifier
                                .weight(1f)
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                        .background(Color.Black.copy(alpha = 0.2f)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat((sliderState.valueRange.endInclusive.toInt() - sliderState.value.toInt()) / 10) {
                        Box(
                            Modifier
                                .weight(1f)
                        ) {
                            if (it != sliderState.valueRange.endInclusive.toInt()) {
                                Box(
                                    Modifier
                                        .background(Color.Black)
                                        .size(2.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        colors = SliderDefaults.colors(
            activeTrackColor = getRatingColor(value),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun RatingSliderPreview() {

    var activeValue by remember { mutableIntStateOf(30) }

    RatingSlider(
        value = activeValue,
        onValueChange = { newValue ->
            activeValue = newValue
        }
    )
}