package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.sliders.AppRangeSlider
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R

@Composable
fun RuntimeSelector(
    modifier: Modifier = Modifier,
    runtimeStart: Int,
    runtimeEnd: Int,
    onValueChanged: (min: Int, max: Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(
                bottom = Dimens.padding.small
            ),
            text = stringResource(R.string.text_runtime),
            style = MaterialTheme.typography.titleMedium
        )

        AppRangeSlider(
            valueRange = 0..390,
            steps = 25,
            startValue = runtimeStart,
            endValue = runtimeEnd,
            onValueChange = onValueChanged
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RuntimeSelectorPreview() {
    RuntimeSelector(
        runtimeStart = 0,
        runtimeEnd = 390,
        onValueChanged = { _, _ -> }
    )
}