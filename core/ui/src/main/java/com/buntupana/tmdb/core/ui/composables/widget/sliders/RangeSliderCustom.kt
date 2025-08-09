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
import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeSliderCustom(
    modifier: Modifier = Modifier,
    @IntRange(from = 0) startValue: Int,
    @IntRange(from = 0) endValue: Int,
    enabled: Boolean = true,
    valueRange: kotlin.ranges.IntRange = 0..100,
    @IntRange(from = 0) steps: Int = 0,
    onValueChange: (startValue: Int, endValue: Int) -> Unit
) {

    val value = startValue.toFloat()..endValue.toFloat()

    RangeSlider(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it.start.toInt(), it.endInclusive.toInt())
        },
        enabled = enabled,
        steps = steps,
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
        onValueChangeFinished = null,
        startThumb = {
            SliderThumb(value = value.start.toInt().toString())
        },
        endThumb = {
            SliderThumb(value = value.endInclusive.toInt().toString())
        },
        track = { sliderState ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {

                val startWeight = value.start / valueRange.endInclusive

                if (startWeight > 0f) {

                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(startWeight)
                            .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                            .background(color = Color.Black.copy(alpha = 0.2f))
                    ) {
                        SliderValueIndicators(indicatorCount = (startWeight * (steps + 1)).roundToInt())
                    }
                }

                val selectionWeight =
                    ((valueRange.endInclusive - value.start) - (valueRange.endInclusive - value.endInclusive)) / valueRange.endInclusive

                if (selectionWeight > 0f) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(selectionWeight)
                            .background(color = SecondaryColor)
                    ) {
                        SliderValueIndicators(indicatorCount = (selectionWeight * (steps + 1)).roundToInt())
                    }
                }

                val endWeight =
                    (valueRange.endInclusive - value.endInclusive) / valueRange.endInclusive

                if (endWeight > 0f) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(endWeight)
                            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                            .background(color = Color.Black.copy(alpha = 0.2f))
                    ) {
                        SliderValueIndicators(indicatorCount = (endWeight * (steps + 1)).roundToInt())
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun RangeSliderCustomPreview() {

    var minDefaultValue by remember { mutableIntStateOf(0) }
    var maxDefaultValue by remember { mutableIntStateOf(80) }

    RangeSliderCustom(
        modifier = Modifier.fillMaxWidth(),
        startValue = minDefaultValue,
        endValue = maxDefaultValue,
        steps = 9,
        onValueChange = { minValue, maxValue ->
            minDefaultValue = minValue
            maxDefaultValue = maxValue
        },
        valueRange = 0..100,
    )
}