package com.buntupana.tmdb.core.ui.composables.widget.sliders

import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import com.buntupana.tmdb.core.ui.util.getRatingColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderCustom(
    modifier: Modifier = Modifier,
    value: Int,
    enabled: Boolean = true,
    valueRange: kotlin.ranges.IntRange = 0..100,
    @IntRange(from = 0) steps: Int = 0,
    onValueChange: (Int) -> Unit,
    selectedBackgroundBrush: Brush? = null
) {

    Slider(
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        value = value.toFloat(),
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
        steps = steps,
        onValueChange = { newValue ->
            onValueChange(newValue.toInt())
        },
        thumb = {
            SliderThumb(value = value.toString())
        },
        track = { sliderState ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {

                val startWeight = value.toFloat() / valueRange.last.toFloat()

                if (startWeight > 0) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .weight(startWeight)
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                            .then(
                                if (selectedBackgroundBrush != null) {
                                    Modifier.background(selectedBackgroundBrush)
                                } else {
                                    Modifier.background(SecondaryColor)
                                }
                            )
                    ) {
                        SliderValueIndicators(indicatorCount = (startWeight * (steps + 1)).roundToInt())
                    }
                }

                val endWeight = 1f - startWeight

                if (endWeight > 0) {
                    Box(
                        Modifier
                            .weight(endWeight)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                            .background(Color.Black.copy(alpha = 0.2f))
                    ) {
                        SliderValueIndicators(
                            indicatorCount = (endWeight *  (steps + 1)).roundToInt()
                        )
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
private fun SliderCustomPreview() {

    var value by remember { mutableIntStateOf(30) }

    SliderCustom(
        modifier = Modifier,
        value = value,
        steps = 9,
        valueRange =  0..500,
        onValueChange = { newValue ->
            value = newValue
        }
    )
}