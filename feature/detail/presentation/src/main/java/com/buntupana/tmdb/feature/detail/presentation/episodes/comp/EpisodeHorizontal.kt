package com.buntupana.tmdb.feature.detail.presentation.episodes.comp

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.AppCard
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun EpisodeHorizontal(
    modifier: Modifier = Modifier,
    isLogged: Boolean,
    episode: Episode,
    seasonNumber: Int,
    onRateClick: () -> Unit = {},
    onSeeMoreClick: () -> Unit
) {

    AppCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation),
        shape = RoundedCornerShape(Dimens.posterRound)
    ) {

        Column {
            ImageFromUrl(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(Dimens.aspectRatioMediaEpisode),
                episode.stillUrl
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.padding.horizontal, Dimens.padding.vertical)
            ) {

                Row {
                    Column {
                        Text(
                            text = episode.episodeNumber.toString(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = Dimens.padding.horizontal)
                    ) {
                        Text(
                            text = episode.name,
                            style = MaterialTheme.typography.titleLarge
                        )
                        EpisodeSubtitle(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.padding.small),
                            // Added season 0 limitation to rate because a bug in the API (https://www.themoviedb.org/talk/67b7098c0c61025fafc3fb2b)
                            isLogged = isLogged && seasonNumber > 0,
                            episode = episode,
                            onRateClick = onRateClick
                        )
                    }
                }

                if (episode.overview.isNotNullOrBlank()) {
                    Text(
                        modifier = Modifier.padding(top = Dimens.padding.vertical),
                        text = episode.overview!!,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (episode.castList.isEmpty() || episode.personCrewMap.orEmpty().isEmpty()) return@Column

            HorizontalDivider()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSeeMoreClick)
                    .padding(vertical = Dimens.padding.vertical),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "See more...",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true,
)
@Composable
fun EpisodeHorizontalPreview() {
    AppTheme {
        EpisodeHorizontal(
            episode = episodeSample,
            isLogged = true,
            seasonNumber = 1,
            onSeeMoreClick = {}
        )
    }
}