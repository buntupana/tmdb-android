package com.buntupana.tmdb.feature.discover.presentation.media_filter.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.buntupana.tmdb.core.ui.composables.widget.ChipSelector
import com.buntupana.tmdb.core.ui.composables.widget.DatePickerTextBox
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.discover.presentation.R
import java.time.LocalDate
import com.buntupana.tmdb.core.ui.R as RCore

@Composable
fun ReleaseDates(
    modifier: Modifier = Modifier,
    releaseTypeList: List<SelectableItem>,
    releaseDateFrom: LocalDate?,
    releaseDateTo: LocalDate?,
    onReleaseTypeListChange: (genreList: List<SelectableItem>) -> Unit,
    onSelectReleaseDateRange: (releaseDateFrom: LocalDate?, releaseDateTo: LocalDate?) -> Unit,
) {
    Column(modifier = modifier) {

        ChipSelector(
            modifier = Modifier,
            title = stringResource(R.string.text_release_dates),
            chipItemList = releaseTypeList,
            showAll = true,
            onSelectionChanged = onReleaseTypeListChange
        )

        DatePickerTextBox(
            modifier = Modifier
                .padding(
                    vertical = Dimens.padding.small
                ),
            label = stringResource(RCore.string.text_from),
            localDate = releaseDateFrom,
            onValueChange = { onSelectReleaseDateRange(it, releaseDateTo) },
        )

        DatePickerTextBox(
            modifier = Modifier
                .padding(
                    top = Dimens.padding.small
                ),
            label = stringResource(RCore.string.text_to),
            localDate = releaseDateTo,
            onValueChange = { onSelectReleaseDateRange(releaseDateFrom, it) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReleaseDatesPreview() {
    ReleaseDates(
        modifier = Modifier,
        releaseTypeList = listOf(SelectableItem(1, UiText.StringResource(R.string.text_release_type_theatrical), true)),
        releaseDateFrom = LocalDate.now(),
        releaseDateTo = LocalDate.now(),
        onReleaseTypeListChange = {},
        onSelectReleaseDateRange = { _, _ -> }
    )
}