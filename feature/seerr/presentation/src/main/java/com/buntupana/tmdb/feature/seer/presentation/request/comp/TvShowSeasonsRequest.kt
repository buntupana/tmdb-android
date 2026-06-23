package com.buntupana.tmdb.feature.seer.presentation.request.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.seer.domain.model.SeerMediaInfo
import com.buntupana.tmdb.feature.seer.domain.model.SeerSeasonInfo
import com.buntupana.tmdb.feature.seer.presentation.R
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import com.panabuntu.tmdb.core.common.model.canBeRequested

@Composable
fun TvShowSeasonsRequest(
    modifier: Modifier = Modifier,
    mediaInfo: SeerMediaInfo,
    selectedSeasons: Set<Int>,
    onSeasonToggle: (seasonNumber: Int) -> Unit,
    onAllSeasonsToggle: () -> Unit = {},
    enable: Boolean = true
) {
    val selectableSeasons = mediaInfo.seasons.orEmpty().filter {
        it.status.canBeRequested()
    }
    val allSelected = selectableSeasons.isNotEmpty() && selectableSeasons.all {
        selectedSeasons.contains(it.seasonNumber)
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.seerr_request_seasons),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            if (selectableSeasons.isNotEmpty()) {
                Switch(
                    checked = allSelected,
                    onCheckedChange = { onAllSeasonsToggle() },
                    enabled = enable
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .padding(vertical = Dimens.padding.small)
        ) {
            items(items = mediaInfo.seasons.orEmpty(), key = { it.seasonNumber }) { season ->
                SeasonRow(
                    enable = enable,
                    season = season,
                    selected = selectedSeasons.contains(season.seasonNumber),
                    onToggle = { onSeasonToggle(season.seasonNumber) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TvShowSeasonsRequestPreview() {
    AppTheme {
        TvShowSeasonsRequest(
            mediaInfo = SeerMediaInfo(
                mediaStatus = SeerrStatus.PARTIALLY_AVAILABLE,
                seasons = listOf(
                    SeerSeasonInfo(
                        seasonNumber = 1,
                        seasonName = "Season 1",
                        status = SeerrStatus.AVAILABLE
                    ),
                    SeerSeasonInfo(
                        seasonNumber = 2,
                        seasonName = "Season 2",
                        status = SeerrStatus.REQUESTED
                    ),
                    SeerSeasonInfo(
                        seasonNumber = 3,
                        seasonName = "Season 4",
                        status = SeerrStatus.PENDING
                    ),
                )
            ),
            selectedSeasons = setOf(1, 3),
            onSeasonToggle = {},
            onAllSeasonsToggle = {}
        )
    }
}

