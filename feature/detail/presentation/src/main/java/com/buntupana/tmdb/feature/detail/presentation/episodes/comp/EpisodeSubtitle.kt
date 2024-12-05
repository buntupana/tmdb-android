package com.buntupana.tmdb.feature.detail.presentation.episodes.comp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.panabuntu.tmdb.core.common.util.formatTime
import com.panabuntu.tmdb.core.common.util.toFullDate


@Composable
fun EpisodeSubtitle(episode: Episode) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        when {

            episode.voteAverage == null || episode.voteCount == 0 -> {}

            episode.voteAverage != 0f -> {
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
                        painter = painterResource(id = R.drawable.ic_star_solid),
                        contentDescription = null,
                    )
                    Text(
                        text = "${(episode.voteAverage!! * 10).toInt()}%",
                        color = MaterialTheme.colorScheme.background
                    )
                }
                Text(
                    text = "  ",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (episode.airDate != null) {
            Text(
                text = episode.airDate!!.toFullDate(),
                fontWeight = FontWeight.Bold
            )
        }

        if (episode.runtime != null) {
            Text(
                text = " • ",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = formatTime(episode.runtime!!),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonSubtitlePreview() {
    EpisodeSubtitle(episode = episodeSample)
}