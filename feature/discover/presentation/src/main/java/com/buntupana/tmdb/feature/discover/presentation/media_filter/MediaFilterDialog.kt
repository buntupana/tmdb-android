package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.buntupana.tmdb.core.ui.R
import com.buntupana.tmdb.core.ui.composables.DropdownMenuText
import com.buntupana.tmdb.core.ui.composables.widget.DatePickerTextBox
import com.buntupana.tmdb.core.ui.dialog.DialogHeader
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterDialog(
    viewModel: MediaFilterViewModel = hiltViewModel(),
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    mediaFilter: MediaFilter,
    onDismiss: () -> Unit,
    onApplyFilterClick: (mediaFilter: MediaFilter) -> Unit,
) {

    if (showDialog.not()) return

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(mediaFilter) {
        viewModel.onEvent(MediaFilterEvent.Init(mediaFilter))
    }

    MediaFilterContent(
        state = viewModel.state,
        sheetState = sheetState,
        onDismiss = {
            coroutineScope.launch {
                sheetState.hide()
                onDismiss()
            }
        },
        onApplySortBy = { sortBySimple, sortByOrder ->
            viewModel.onEvent(MediaFilterEvent.ChangeSortBy(sortBySimple, sortByOrder))
        },
        onSelectAvailabilities = { monetizationType ->
            viewModel.onEvent(MediaFilterEvent.SelectMonetizationType(monetizationType))
        },
        onSelectReleaseType = {
            viewModel.onEvent(MediaFilterEvent.SelectReleaseType(it))
        },
        onSelectReleaseDateRange = { releaseDateFrom, releaseDateTo ->
            viewModel.onEvent(
                MediaFilterEvent.SelectReleaseDateRange(
                    releaseDateFrom = releaseDateFrom,
                    releaseDateTo = releaseDateTo
                )
            )
        },
        onApplyFilterClick = onApplyFilterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterContent(
    state: MediaFilterState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplySortBy: (sortBySimple: SortBySimple, sortByOrder: SortByOrder) -> Unit,
    onSelectAvailabilities: (monetizationType: MonetizationType) -> Unit,
    onSelectReleaseType: (releaseType: ReleaseType) -> Unit,
    onSelectReleaseDateRange: (releaseDateFrom: LocalDate?, releaseDateTo: LocalDate?) -> Unit,
    onApplyFilterClick: (mediaFilter: MediaFilter) -> Unit,
) {

    ModalBottomSheet(
        modifier = Modifier.padding(
            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
        ),
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            DialogHeader(
                modifier = Modifier.fillMaxWidth(),
                title = "Filter",
                onAcceptClick = {
                    onApplyFilterClick(state.mediaFilter)
                    onDismiss()
                },
                onCloseClick = onDismiss
            )

            Spacer(modifier = Modifier.padding(vertical = Dimens.padding.small))

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                text = "Sort By",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.tiny,
                        vertical = Dimens.padding.small
                    )
            ) {

                DropdownMenuText(
                    modifier = Modifier.animateContentSize(),
                    text = stringResource(state.sortBySelected.stringResId),
                    optionMap = SortBySimple.entries.mapIndexed { index, sortBy ->
                        index to stringResource(sortBy.stringResId)
                    }.toMap(),
                    onOptionClicked = { id, value ->
                        onApplySortBy(
                            SortBySimple.entries[id],
                            state.sortByOrderSelected
                        )
                    }
                )

                DropdownMenuText(
                    modifier = Modifier.animateContentSize(),
                    text = stringResource(state.sortByOrderSelected.stringResId),
                    optionMap = SortByOrder.entries.mapIndexed { index, sortBy ->
                        index to stringResource(sortBy.stringResId)
                    }.toMap(),
                    onOptionClicked = { id, value ->
                        onApplySortBy(
                            state.sortBySelected,
                            SortByOrder.entries[id]
                        )
                    }
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                text = "Availabilities",
                style = MaterialTheme.typography.titleMedium
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                horizontalArrangement = Arrangement.spacedBy(Dimens.padding.small)
            ) {

                MonetizationType.entries.forEach {
                    FilterChip(
                        selected = state.mediaFilter.monetizationTypeList.contains(it),
                        onClick = { onSelectAvailabilities(it) },
                        label = {
                            Text(text = stringResource(getAvailabilityResId(it)))
                        }
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                text = "Release Dates",
                style = MaterialTheme.typography.titleMedium
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                horizontalArrangement = Arrangement.spacedBy(Dimens.padding.small)
            ) {

                ReleaseType.entries.forEach {
                    FilterChip(
                        selected = state.mediaFilter.releaseTypeList.contains(it),
                        onClick = { onSelectReleaseType(it) },
                        label = {
                            Text(text = stringResource(getReleaseTypeResId(it)))
                        }
                    )
                }
            }

            DatePickerTextBox(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                label = stringResource(R.string.text_from),
                localDate = state.mediaFilter.releaseDateFrom,
                onValueChange = { onSelectReleaseDateRange(it, state.mediaFilter.releaseDateTo) },
            )

            DatePickerTextBox(
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.small
                    ),
                label = stringResource(R.string.text_to),
                localDate = state.mediaFilter.releaseDateTo,
                onValueChange = { onSelectReleaseDateRange(state.mediaFilter.releaseDateFrom, it) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MediaFilterScreenPreview() {
    MediaFilterContent(
        state = MediaFilterState(
            mediaFilter = MediaFilter(),

            ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = {},
        onApplySortBy = { _, _ -> },
        onSelectAvailabilities = {},
        onSelectReleaseType = {},
        onSelectReleaseDateRange = { _, _ -> },
        onApplyFilterClick = {}
    )
}