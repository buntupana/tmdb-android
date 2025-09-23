package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.ChipSelector
import com.buntupana.tmdb.core.ui.composables.widget.DatePickerTextBox
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.discover.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import java.time.LocalDate
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ReleaseDatesSelector(
    modifier: Modifier = Modifier,
    mediaType: MediaType,
    releaseTypeList: List<SelectableItem>,
    releaseDateFrom: LocalDate?,
    releaseDateTo: LocalDate?,
    isSearchFirstAirDateSelected: Boolean,
    onReleaseTypeListChange: (genreList: List<SelectableItem>) -> Unit,
    onSelectReleaseDateRange: (releaseDateFrom: LocalDate?, releaseDateTo: LocalDate?) -> Unit,
    onSearchFirstAirDateChange: (isFirstAirDateSelected: Boolean) -> Unit,
) {
    Column(modifier = modifier) {

        val titleStringResId = when (mediaType) {
            MediaType.MOVIE -> R.string.discover_release_dates
            MediaType.TV_SHOW -> R.string.discover_air_dates
        }

        Text(
            modifier = Modifier
                .padding(bottom = Dimens.padding.small),
            text = stringResource(titleStringResId),
            style = MaterialTheme.typography.titleMedium
        )

        if (releaseTypeList.isNotEmpty() && mediaType == MediaType.MOVIE) {
            ChipSelector(
                modifier = Modifier.fillMaxWidth(),
                chipItemList = releaseTypeList,
                showAllChip = true,
                onSelectionChanged = onReleaseTypeListChange
            )
        }

        DatePickerTextBox(
            modifier = Modifier
                .padding(
                    vertical = Dimens.padding.small
                ),
            label = stringResource(RCore.string.common_from),
            localDate = releaseDateFrom,
            onValueChange = { onSelectReleaseDateRange(it, releaseDateTo) },
        )

        DatePickerTextBox(
            modifier = Modifier
                .padding(
                    top = Dimens.padding.small
                ),
            label = stringResource(RCore.string.common_to),
            localDate = releaseDateTo,
            onValueChange = { onSelectReleaseDateRange(releaseDateFrom, it) },
        )

        if (mediaType == MediaType.TV_SHOW) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.padding.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = Dimens.padding.horizontal),
                    text = stringResource(R.string.discover_search_first_air_date),
                    style = MaterialTheme.typography.bodyLarge
                )

                Switch(
                    modifier = Modifier,
                    checked = isSearchFirstAirDateSelected,
                    colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.primary),
                    onCheckedChange = onSearchFirstAirDateChange

                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReleaseDatesSelectorPreview() {
    ReleaseDatesSelector(
        modifier = Modifier.fillMaxWidth(),
        releaseTypeList = listOf(
            SelectableItem(
                1,
                UiText.StringResource(R.string.discover_release_type_theatrical),
                true
            )
        ),
        mediaType = MediaType.TV_SHOW,
        releaseDateFrom = LocalDate.now(),
        releaseDateTo = LocalDate.now(),
        isSearchFirstAirDateSelected = true,
        onReleaseTypeListChange = {},
        onSelectReleaseDateRange = { _, _ -> },
        onSearchFirstAirDateChange = {}
    )
}