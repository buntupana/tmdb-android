package com.buntupana.tmdb.core.ui.composables.widget.sliders

import androidx.annotation.IntRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.util.getRatingColor
import com.panabuntu.tmdb.core.common.util.Const


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingSlider(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @IntRange(
        from = Const.MIN_RATING_VALUE.toLong(),
        to = Const.MAX_RATING_VALUE.toLong()
    ) value: Int,
    onValueChange: (Int) -> Unit
) {

    SliderCustom(
        modifier = modifier,
        enabled = enabled,
        value = value,
        valueRange = 0..Const.MAX_RATING_VALUE,
        steps = 9,
        onValueChange = onValueChange,
        selectedBackgroundBrush = Brush.horizontalGradient(
            listOf(
                getRatingColor(value).copy(alpha = 0.3f),
                getRatingColor(value)
            )
        )
    )
}

@Preview(showBackground = true)
@Composable
fun RatingSliderPreview() {

    var activeValue by remember { mutableIntStateOf(80) }

    RatingSlider(
        value = activeValue,
        onValueChange = { newValue ->
            activeValue = newValue
        }
    )
}