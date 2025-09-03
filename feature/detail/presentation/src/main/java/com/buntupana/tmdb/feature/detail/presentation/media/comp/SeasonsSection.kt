package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.TextButton
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.common.SeasonSubtitle
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.buntupana.tmdb.feature.detail.presentation.seasonSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank
import com.panabuntu.tmdb.core.common.util.toFullDate
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun SeasonsSection(
    modifier: Modifier = Modifier,
    seasonList: List<Season>,
    isInAir: Boolean,
    lastEpisode: Episode?,
    nextEpisode: Episode?,
    onLastSeasonClick: (season: Season) -> Unit,
    onAllSeasonsClick: () -> Unit
) {

    if (seasonList.isEmpty()) return

    val titleStrRes = if (isInAir) {
        R.string.text_current_season
    } else {
        R.string.text_last_season
    }

    val lastSeason = seasonList.last()

    Column(
        modifier = modifier
    ) {
        HorizontalDivider()

        Text(
            modifier = Modifier.padding(
                horizontal = Dimens.padding.horizontal,
                vertical = Dimens.padding.vertical
            ),
            text = stringResource(id = titleStrRes),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Card(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.small
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
            shape = RoundedCornerShape(Dimens.posterRound),
            onClick = { onLastSeasonClick(lastSeason) }
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.vertical
                    )
            ) {

                Text(
                    text = lastSeason.name,
                    style = MaterialTheme.typography.titleLarge
                )

                SeasonSubtitle(season = lastSeason)

                if (lastSeason.overview.isNotNullOrBlank()) {
                    Text(
                        modifier = Modifier.padding(top = Dimens.padding.vertical),
                        text = lastSeason.overview!!
                    )
                }

                LastEpisode(episode = nextEpisode ?: lastEpisode)
            }
        }

        TextButton(
            onClick = onAllSeasonsClick,
            rippleColor = MaterialTheme.colorScheme.onBackground
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = Dimens.padding.small),
                text = stringResource(id = R.string.text_view_all_seasons),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun LastEpisode(episode: Episode?) {

    episode ?: return

    Row(
        modifier = Modifier.padding(top = Dimens.padding.vertical),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .size(Dimens.icon)
                .padding(end = Dimens.padding.betweenTexts),
            painter = painterResource(id = RCore.drawable.ic_calendar),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = null
        )

        Text(buildAnnotatedString {

            append(episode.name)

            append(" (")

            append("${episode.seasonNumber.toString()}x${episode.episodeNumber}")

            if (episode.airDate != null) {
                append(", ")
                append(episode.airDate!!.toFullDate())
            }

            append(")")
        })
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonsPreview() {
    AppTheme(darkTheme = true) {
        SeasonsSection(
            modifier = Modifier.fillMaxWidth(),
            isInAir = false,
            seasonList = listOf(seasonSample, seasonSample, seasonSample),
            lastEpisode = episodeSample,
            nextEpisode = episodeSample,
            onAllSeasonsClick = {},
            onLastSeasonClick = { _ -> }
        )
    }
}