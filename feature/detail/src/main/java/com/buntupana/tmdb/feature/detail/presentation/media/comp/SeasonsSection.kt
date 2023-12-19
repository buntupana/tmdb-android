package com.buntupana.tmdb.feature.detail.presentation.media.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.core.presentation.util.isNotNullOrBlank
import com.buntupana.tmdb.core.presentation.util.toFullDate
import com.buntupana.tmdb.feature.detail.R
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.buntupana.tmdb.feature.detail.presentation.seasonSample

@Composable
fun SeasonsSection(
    modifier: Modifier = Modifier,
    seasonList: List<Season>,
    isInAir: Boolean,
    lastEpisode: Episode?,
    nextEpisode: Episode?,
    onLastSeasonClick: (seasonId: Long) -> Unit,
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
        Divider()

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
                .clickable { onLastSeasonClick(lastSeason.id) }
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.small
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
            shape = RoundedCornerShape(Dimens.posterRound)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
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
                        text = lastSeason.overview
                    )
                }

                LastEpisode(episode = nextEpisode ?: lastEpisode)
            }
        }

        Text(
            modifier = Modifier
                .clickable {
                    onAllSeasonsClick()
                }
                .padding(
                    horizontal = Dimens.padding.horizontal,
                    vertical = Dimens.padding.vertical
                ),
            text = stringResource(id = R.string.text_view_all_seasons),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun SeasonSubtitle(season: Season) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (season.voteAverage != null) {

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(Dimens.posterRound))
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 2.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    painter = painterResource(id = com.buntupana.tmdb.core.R.drawable.ic_star_solid),
                    contentDescription = null,
                )
                Text(
                    text = season.voteAverage.toString(),
                    color = MaterialTheme.colorScheme.background
                )
            }
        }

        Spacer(modifier = Modifier.padding(Dimens.padding.betweenTexts))

        if (season.airDate != null) {
            Text(
                text = season.airDate.year.toString(),
                fontWeight = FontWeight.Bold
            )
        }

        if (season.airDate != null && season.episodeCount != null) {
            Text(
                text = " • ",
                fontWeight = FontWeight.Bold
            )
        }

        if (season.episodeCount != null) {
            Text(
                text = pluralStringResource(
                    id = R.plurals.text_episodes_count,
                    count = season.episodeCount,
                    season.episodeCount
                ),
                fontWeight = FontWeight.Bold
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

        Image(
            modifier = Modifier
                .size(Dimens.icon)
                .padding(end = Dimens.padding.betweenTexts),
            painter = painterResource(id = com.buntupana.tmdb.core.R.drawable.ic_calendar),
            contentDescription = null
        )

        Text(buildAnnotatedString {

            append(episode.name.orEmpty())

            append(" (")

            append("${episode.seasonNumber.toString()}x${episode.episodeNumber}")

            if (episode.airDate != null) {
                append(", ")
                append(episode.airDate.toFullDate())
            }

            append(")")
        })
    }
}

@Preview(showBackground = true)
@Composable
fun SeasonsPreview() {
    SeasonsSection(
        modifier = Modifier.fillMaxWidth(),
        isInAir = false,
        seasonList = listOf(seasonSample, seasonSample, seasonSample),
        lastEpisode = episodeSample,
        nextEpisode = episodeSample,
        onAllSeasonsClick = {},
        onLastSeasonClick = {}
    )
}