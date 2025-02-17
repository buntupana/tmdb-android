package com.buntupana.tmdb.feature.detail.presentation.episodes.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.ImageFromUrl
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.panabuntu.tmdb.core.common.util.isNotNullOrBlank

@Composable
fun EpisodeHorizontal(
    modifier: Modifier = Modifier,
    isLogged: Boolean,
    episode: Episode,
    onRateClick: () -> Unit = {},
    onItemClick: () -> Unit
) {

    Surface(
        modifier = modifier,
        shadowElevation = Dimens.cardElevation,
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
                                .padding(top = Dimens.padding.small)
                            ,
                            isLogged = isLogged,
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
        }
    }
}

@Preview
@Composable
fun EpisodeHorizontalPreview() {
    EpisodeHorizontal(
        episode = episodeSample,
        isLogged = true,
        onItemClick = {}
    )
}