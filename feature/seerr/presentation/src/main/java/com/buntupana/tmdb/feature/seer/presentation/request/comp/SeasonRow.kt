package com.buntupana.tmdb.feature.seer.presentation.request.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.seer.SeerrStatusLabel
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.seer.domain.model.SeerSeasonInfo
import com.buntupana.tmdb.feature.seer.presentation.R
import com.panabuntu.tmdb.core.common.model.SeerrStatus

@Composable
fun SeasonRow(
    season: SeerSeasonInfo,
    selected: Boolean,
    onToggle: () -> Unit,
    enable: Boolean = true
) {
    val isAvailable = season.status == SeerrStatus.AVAILABLE
    val isRequested = season.status == SeerrStatus.REQUESTED

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.padding.tiny),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val seasonName = if (season.seasonName != null) {
            season.seasonName!!
        } else  {
            stringResource(R.string.seerr_request_season_format, season.seasonNumber)
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = Dimens.padding.medium),
            text = seasonName,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
        if (isAvailable || isRequested) {
            SeerrStatusLabel(
                modifier = Modifier.padding(start = Dimens.padding.small),
                status = season.status
            )
        } else {
            Switch(
                modifier = Modifier.padding(start = Dimens.padding.small),
                checked = selected,
                onCheckedChange = { onToggle() },
                enabled = enable
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SeasonRowPreview() {
    AppTheme {
        Column {
            SeasonRow(
                season = SeerSeasonInfo(
                    seasonNumber = 1,
                    seasonName = "Season 1",
                    status = SeerrStatus.UNKNOWN
                ),
                selected = false,
                onToggle = {},
            )
            SeasonRow(
                season = SeerSeasonInfo(
                    seasonNumber = 2,
                    seasonName = "Season 2",
                    status = SeerrStatus.UNKNOWN
                ),
                selected = true,
                onToggle = {},
            )
            SeasonRow(
                season = SeerSeasonInfo(
                    seasonNumber = 3,
                    seasonName = "Season 3 asdf asdf adsf adf adsf adsf adsf asdf adsf asdf asdf ",
                    status = SeerrStatus.AVAILABLE
                ),
                selected = false,
                onToggle = {},
            )
            SeasonRow(
                season = SeerSeasonInfo(
                    seasonNumber = 4,
                    seasonName = "Season 4",
                    status = SeerrStatus.REQUESTED
                ),
                selected = false,
                onToggle = {},
            )
        }
    }
}
