package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.widget.ChipSelector
import com.buntupana.tmdb.core.ui.dialog.DialogHeader
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.feature.discover.domain.entity.Genre
import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.presentation.R
import com.buntupana.tmdb.feature.discover.presentation.mapper.toSelectableItem
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.MinUserVotesSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.ReleaseDatesSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.RuntimeSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.SortBySelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.UserScoreRangeSelector
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterDialog(
    viewModel: MediaFilterViewModel = hiltViewModel(),
    showDialog: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    mediaListFilter: MediaListFilter,
    onDismiss: () -> Unit,
    onApplyFilterClick: (mediaListFilter: MediaListFilter) -> Unit,
) {

    if (showDialog.not()) return

    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(mediaListFilter) {
        viewModel.onEvent(MediaFilterEvent.Init(mediaListFilter))
    }

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("MediaFilterDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        is MediaFilterSideEffect.ApplyFilters -> {
                            coroutineScope.launch {
                                onApplyFilterClick(sideEffect.mediaListFilter)
                                sheetState.hide()
                                onDismiss()
                            }
                        }
                    }
                }
            }
        }
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
        onAvailabilityListChanged = { monetizationTypeList ->
            viewModel.onEvent(MediaFilterEvent.SelectMonetizationType(monetizationTypeList))
        },
        onReleaseTypeSelectedListChanged = {
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
        onGenreSelectedListChanged = {
            viewModel.onEvent(MediaFilterEvent.SelectGenreNew(it))
        },
        onUserScoreRangeSelected = { min, max, includeNotRated ->
            viewModel.onEvent(
                MediaFilterEvent.SelectUserScoreRange(
                    min = min,
                    max = max,
                    includeNotRated = includeNotRated
                )
            )
        },
        onMinUserVotesChanged = {
            viewModel.onEvent(MediaFilterEvent.SelectMinUserVotes(it))
        },
        onRuntimeRangeSelected = { min, max ->
            viewModel.onEvent(MediaFilterEvent.SelectRuntimeRange(min, max))
        },
        onApplyFilterClick = {
            viewModel.onEvent(MediaFilterEvent.ApplyFilter)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterContent(
    state: MediaFilterState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onApplySortBy: (sortBySimple: SortBySimple, sortByOrder: SortByOrder) -> Unit,
    onAvailabilityListChanged: (genreList: List<SelectableItem>) -> Unit,
    onReleaseTypeSelectedListChanged: (releaseTypeList: List<SelectableItem>) -> Unit,
    onSelectReleaseDateRange: (releaseDateFrom: LocalDate?, releaseDateTo: LocalDate?) -> Unit,
    onGenreSelectedListChanged: (genreList: List<SelectableItem>) -> Unit,
    onUserScoreRangeSelected: (min: Int, max: Int, includeNotRated: Boolean) -> Unit,
    onMinUserVotesChanged: (minUserVotes: Int) -> Unit,
    onRuntimeRangeSelected: (min: Int, max: Int) -> Unit,
    onApplyFilterClick: () -> Unit,
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
                    onApplyFilterClick()
                },
                onCloseClick = onDismiss
            )

            Column(
                modifier = Modifier.verticalScroll(state = rememberScrollState())
            ) {

                SortBySelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.small
                        ),
                    sortBySelected = state.sortBySelected,
                    sortByOrderSelected = state.sortByOrderSelected,
                    onApplySortBy = onApplySortBy
                )


                ChipSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.medium
                        ),
                    title = stringResource(R.string.text_availabilities),
                    chipItemList = state.availabilitiesList,
                    showAll = true,
                    onSelectionChanged = onAvailabilityListChanged
                )

                ReleaseDatesSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.small
                        ),
                    releaseTypeList = state.releaseTypesList,
                    releaseDateFrom = state.releaseDateFrom,
                    releaseDateTo = state.releaseDateTo,
                    onSelectReleaseDateRange = onSelectReleaseDateRange,
                    onReleaseTypeListChange = onReleaseTypeSelectedListChanged
                )

                ChipSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.medium
                        ),
                    title = stringResource(R.string.text_genres),
                    chipItemList = state.genreList,
                    showAll = false,
                    onSelectionChanged = onGenreSelectedListChanged
                )

                UserScoreRangeSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.medium
                        ),
                    userScoreMin = state.minUserScore,
                    userScoreMax = state.maxUserScore,
                    includeNotRated = state.includeNotRated,
                    onUserScoreRangeChanged = onUserScoreRangeSelected
                )

                MinUserVotesSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.small
                        ),
                    minUserVotes = state.minUserVotes,
                    onValueChange = onMinUserVotesChanged
                )

                RuntimeSelector(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.small
                        ),
                    runtimeStart = state.runtimeMin,
                    runtimeEnd = state.runtimeMax,
                    onValueChanged = onRuntimeRangeSelected
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, heightDp = 2000)
@Composable
fun MediaFilterScreenPreview() {
    MediaFilterContent(
        state = MediaFilterState(
            availabilitiesList = MonetizationType.entries.map { it.toSelectableItem(0) },
            releaseTypesList = ReleaseType.entries.map { it.toSelectableItem(0) },
            genreList = Genre.entries.map { it.toSelectableItem(0) }
        ),
        sheetState = SheetState(
            skipPartiallyExpanded = true,
            LocalDensity.current,
            initialValue = SheetValue.Expanded
        ),
        onDismiss = {},
        onApplySortBy = { _, _ -> },
        onAvailabilityListChanged = {},
        onReleaseTypeSelectedListChanged = {},
        onSelectReleaseDateRange = { _, _ -> },
        onGenreSelectedListChanged = {},
        onUserScoreRangeSelected = { _, _, _ -> },
        onMinUserVotesChanged = {},
        onRuntimeRangeSelected = { _, _ -> },
        onApplyFilterClick = {}
    )
}