package com.buntupana.tmdb.feature.detail.presentation.common

import android.content.res.Configuration
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
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.detail.domain.model.Season
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.detail.presentation.seasonSample
import java.time.LocalDate
import com.buntupana.tmdb.core.ui.R as RCore


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
        } else if(season.voteAverage != 0f) {
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
                    painter = painterResource(id = RCore.drawable.ic_star_solid),
                    contentDescription = null,
                )
                Text(
                    text = "${(season.voteAverage * 10).toInt()}%",
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
                text = season.airDate!!.year.toString(),
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
                    id = R.plurals.detail_episodes_count,
                    count = season.episodeCount!!,
                    season.episodeCount!!
                ),
                fontWeight = FontWeight.Bold
            )
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
private fun SeasonSubtitlePreview() {
    AppTheme {
        SeasonSubtitle(season = seasonSample)
    }
}