package com.buntupana.tmdb.feature.detail.presentation.episodes.comp

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.ui.composables.VerticalNumberRoulette
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.getOnBackgroundColor
import com.buntupana.tmdb.feature.detail.domain.model.Episode
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.episodeSample
import com.panabuntu.tmdb.core.common.util.formatTime
import com.panabuntu.tmdb.core.common.util.toFullDate
import com.buntupana.tmdb.core.ui.R as RCore


@Composable
fun EpisodeSubtitle(
    modifier: Modifier = Modifier,
    isLogged: Boolean,
    episode: Episode,
    onRateClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {

        Row {
            if (episode.airDate != null) {
                Text(
                    text = episode.airDate!!.toFullDate(),
                    fontWeight = FontWeight.Bold
                )
            }

            if (episode.runtime != null && episode.airDate != null) {
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

        Spacer(modifier = Modifier.height(Dimens.padding.small))

        // The web allows to rate any episode besides it's release date, so for now it's this line is disabled
//        if (episode.isRateable.not()) return@Column

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimens.posterRound))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(Dimens.posterRound),
                    color = MaterialTheme.colorScheme.primaryContainer
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 2.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.background),
                    painter = painterResource(id = RCore.drawable.ic_star_solid),
                    contentDescription = null,
                )
                Text(
                    text = "${(episode.voteAverage!! * 10).toInt()}%",
                    color = MaterialTheme.colorScheme.background
                )
            }

            if (isLogged) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .animateContentSize()
                        .clickable { onRateClick() }
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (episode.userRating == null) {
                        Text(
                            text = stringResource(R.string.text_rate_it),
                            color = MaterialTheme.colorScheme.secondaryContainer.getOnBackgroundColor()
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.text_yours_is) + " ",
                            color = MaterialTheme.colorScheme.secondaryContainer.getOnBackgroundColor()
                        )
                        VerticalNumberRoulette(
                            value = episode.userRating,
                            color = MaterialTheme.colorScheme.secondaryContainer.getOnBackgroundColor()
                        )
                        Text(
                            text = "%",
                            color = MaterialTheme.colorScheme.secondaryContainer.getOnBackgroundColor()
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark",
    showBackground = true
)
@Composable
private fun SeasonSubtitlePreview() {
    AppTheme {
        EpisodeSubtitle(
            episode = episodeSample,
            isLogged = true
        )
    }
}