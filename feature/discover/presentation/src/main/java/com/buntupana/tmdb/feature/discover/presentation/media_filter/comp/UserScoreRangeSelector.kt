package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.annotation.IntRange
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.sliders.AppRangeSlider
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R
import com.panabuntu.tmdb.core.common.util.Const

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScoreRangeSelector(
    modifier: Modifier = Modifier,
    @IntRange(
        from = Const.MIN_RATING_VALUE.toLong(),
        to = Const.MAX_RATING_VALUE.toLong()
    ) userScoreMin: Int,
    @IntRange(
        from = Const.MIN_RATING_VALUE.toLong(),
        to = Const.MAX_RATING_VALUE.toLong()
    ) userScoreMax: Int,
    includeNotRated: Boolean,
    onUserScoreRangeChanged: (min: Int, max: Int, includeNotRated: Boolean) -> Unit
) {
    Column(modifier = modifier) {

        Text(
            modifier = Modifier,
            text = stringResource(R.string.text_user_score),
            style = MaterialTheme.typography.titleMedium
        )

        AppRangeSlider(
            modifier = Modifier.fillMaxWidth(),
            startValue = userScoreMin,
            endValue = userScoreMax,
            steps = 9,
            valueRange = 0..100,
            onValueChange = { startValue, endValue ->
                onUserScoreRangeChanged(startValue, endValue, includeNotRated)
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.padding(horizontal = Dimens.padding.horizontal),
                text = stringResource(R.string.text_include_not_rated),
                style = MaterialTheme.typography.bodyLarge
            )

            Switch(
                modifier = Modifier,
                checked = includeNotRated,
                colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary),
                onCheckedChange = {
                    onUserScoreRangeChanged(userScoreMin, userScoreMax, it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserScoreRangeSelectorPreview() {
    AppTheme {
        UserScoreRangeSelector(
            modifier = Modifier,
            userScoreMin = 20,
            userScoreMax = 80,
            includeNotRated = true,
            onUserScoreRangeChanged = { _, _, _ -> }
        )
    }
}