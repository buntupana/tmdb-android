package com.buntupana.tmdb.feature.detail.presentation.seasons.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.presentation.composables.ImageFromUrl
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.isNotNullOrBlank
import com.buntupana.tmdb.core.presentation.util.toFullDate
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.common.SeasonSubtitle
import com.buntupana.tmdb.feature.detail.presentation.seasonSample

@Composable
fun SeasonItem(
    modifier: Modifier = Modifier,
    tvShowName: String,
    season: Season,
    onSeasonClick: () -> Unit
) {

    Row(
        modifier = modifier
            .clickable {
                onSeasonClick()
            }
            .padding(horizontal = Dimens.padding.horizontal, vertical = Dimens.padding.vertical)
    ) {

        ImageFromUrl(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.posterRound))
                .height(Dimens.imageSize.posterHeight)
                .aspectRatio(Dimens.aspectRatioMediaPoster),
            imageUrl = season.posterUrl
        )

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(
                    horizontal = Dimens.padding.horizontal
                )
        ) {

            Text(
                text = season.name,
                style = MaterialTheme.typography.titleLarge
            )

            SeasonSubtitle(season = season)

            if (season.airDate != null) {
                Text(
                    modifier = Modifier.padding(top = Dimens.padding.vertical),
                    text = stringResource(
                        id = R.string.message_season_premiered,
                        season.name,
                        tvShowName,
                        season.airDate.toFullDate()
                    )
                )
            }

            if (season.overview.isNotNullOrBlank()) {
                Text(
                    modifier = Modifier.padding(top = Dimens.padding.vertical),
                    text = season.overview
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun SeasonItemPreview() {
    SeasonItem(
        tvShowName = "Jack Reacher",
        season = seasonSample,
        onSeasonClick = {}
    )
}