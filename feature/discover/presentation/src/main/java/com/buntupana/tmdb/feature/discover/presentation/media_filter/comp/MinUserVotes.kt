package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.sliders.SliderCustom
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.presentation.R

@Composable
fun MinUserVotes(
    modifier: Modifier = Modifier,
    minUserVotes: Int,
    onValueChange: (minUserVotes: Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(
                bottom = Dimens.padding.small
            ),
            text = stringResource(R.string.text_min_user_votes),
            style = MaterialTheme.typography.titleMedium
        )

        SliderCustom(
            valueRange = 0..500,
            steps = 9,
            value = minUserVotes,
            onValueChange = onValueChange
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MinUserVotesPreview() {
    MinUserVotes(
        minUserVotes = 100,
        onValueChange = {}
    )
}