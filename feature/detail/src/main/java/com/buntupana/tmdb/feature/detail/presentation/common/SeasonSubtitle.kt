package com.buntupana.tmdb.feature.detail.presentation.common

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
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buntupana.tmdb.core.R
import com.buntupana.tmdb.core.presentation.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.seasonSample
import java.time.LocalDate


@Composable
fun SeasonSubtitle(season: Season) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (season.voteAverage == 0f && season.airDate?.isAfter(LocalDate.now()) != false) {
            Text(
                text = " — ",
                fontWeight = FontWeight.Bold
            )
        } else {
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
                    text = season.voteAverage.toString(),
                    color = MaterialTheme.colorScheme.background
                )
            }
            Text(
                text = "  ",
                fontWeight = FontWeight.Bold
            )
        }

        if (season.airDate != null) {
            Text(
                text = season.airDate.year.toString(),
                fontWeight = FontWeight.Bold
            )
        }

        if (season.episodeCount != null) {
            Text(
                text = " • ",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = pluralStringResource(
                    id = com.buntupana.tmdb.feature.detail.R.plurals.text_episodes_count,
                    count = season.episodeCount,
                    season.episodeCount
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonSubtitlePreview() {
    SeasonSubtitle(season = seasonSample)
}