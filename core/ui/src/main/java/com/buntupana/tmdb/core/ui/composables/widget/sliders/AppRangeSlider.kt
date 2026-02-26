package com.buntupana.tmdb.core.ui.composables.widget.sliders

import android.content.res.Configuration
import androidx.annotation.IntRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.theme.AppTheme
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRangeSlider(
    modifier: Modifier = Modifier,
    @IntRange(from = 0) startValue: Int,
    @IntRange(from = 0) endValue: Int,
    enabled: Boolean = true,
    valueRange: kotlin.ranges.IntRange = 0..100,
    @IntRange(from = 0) steps: Int = 0,
    onValueChange: (startValue: Int, endValue: Int) -> Unit
) {

    val startValue = if (startValue > endValue) endValue else startValue
    val endValue = if (endValue > valueRange.last) valueRange.last else endValue
    val value = startValue.toFloat()..endValue.toFloat()

    RangeSlider(
        modifier = modifier,
        value = value,
        onValueChange = {
            onValueChange(it.start.roundToInt(), it.endInclusive.roundToInt())
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
                            .background(MaterialTheme.colorScheme.outline)
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
                            .background(color = MaterialTheme.colorScheme.secondaryContainer)
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
                            .background(MaterialTheme.colorScheme.outline)
                    ) {
                        SliderValueIndicators(indicatorCount = (endWeight * (steps + 1)).roundToInt())
                    }
                }
            }
        }
    )
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
private fun AppRangeSliderPreview() {

    var minDefaultValue by remember { mutableIntStateOf(100) }
    var maxDefaultValue by remember { mutableIntStateOf(300) }

    AppTheme {
        AppRangeSlider(
            modifier = Modifier.fillMaxWidth(),
            startValue = minDefaultValue,
            endValue = maxDefaultValue,
            steps = 25,
            onValueChange = { minValue, maxValue ->
                minDefaultValue = minValue
                maxDefaultValue = maxValue
            },
            valueRange = 0..390,
        )
    }
}