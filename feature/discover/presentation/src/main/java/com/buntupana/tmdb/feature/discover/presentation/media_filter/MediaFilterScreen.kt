package com.buntupana.tmdb.feature.discover.presentation.media_filter

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.widget.ChipSelector
import com.buntupana.tmdb.core.ui.theme.AppTheme
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.SetSystemBarsColors
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieGenre
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.presentation.R
import com.buntupana.tmdb.feature.discover.presentation.mapper.toSelectableItem
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.MediaFilterTopBar
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.MinUserVotesSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.ReleaseDatesSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.RuntimeSelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.SortBySelector
import com.buntupana.tmdb.feature.discover.presentation.media_filter.comp.UserScoreRangeSelector
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterScreen(
    viewModel: MediaFilterViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onApplyFilterClick: (movieListFilter: MediaListFilter) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                viewModel.sideEffect.collect { sideEffect ->
                    Timber.d("MediaFilterDialog: sideEffect = $sideEffect")
                    when (sideEffect) {
                        is MediaFilterSideEffect.ApplyFilters -> {
                            coroutineScope.launch {
                                onApplyFilterClick(sideEffect.mediaListFilter)
                            }
                        }
                    }
                }
            }
        }
    }

    MediaFilterContent(
        state = viewModel.state,
        onBackClick = onBackClick,
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
        onSearchFirstAirDateChange = {
            viewModel.onEvent(MediaFilterEvent.SelectSearchFirstAirDate(it))
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
    onBackClick: () -> Unit,
    onApplySortBy: (sortBySimple: SortBySimple, sortByOrder: SortByOrder) -> Unit,
    onAvailabilityListChanged: (genreList: List<SelectableItem>) -> Unit,
    onReleaseTypeSelectedListChanged: (releaseTypeList: List<SelectableItem>) -> Unit,
    onSelectReleaseDateRange: (releaseDateFrom: LocalDate?, releaseDateTo: LocalDate?) -> Unit,
    onSearchFirstAirDateChange: (searchFirstAirDate: Boolean) -> Unit,
    onGenreSelectedListChanged: (genreList: List<SelectableItem>) -> Unit,
    onUserScoreRangeSelected: (min: Int, max: Int, includeNotRated: Boolean) -> Unit,
    onMinUserVotesChanged: (minUserVotes: Int) -> Unit,
    onRuntimeRangeSelected: (min: Int, max: Int) -> Unit,
    onApplyFilterClick: () -> Unit,
) {

    SetSystemBarsColors(
        statusBarColor = MaterialTheme.colorScheme.primaryContainer,
        navigationBarColor = MaterialTheme.colorScheme.primaryContainer,
        translucentNavigationBar = true
    )

    Scaffold(
        topBar = {
            MediaFilterTopBar(
                mediaType = state.mediaType,
                onApplyClick = onApplyFilterClick,
                onSaveClick = {},
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(state = rememberScrollState())
        ) {

            SortBySelector(
                modifier = Modifier
                    .padding(
                        top = Dimens.padding.medium,
                        bottom = Dimens.padding.small
                    )
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                    ),
                sortBySelected = state.sortBySelected,
                sortByOrderSelected = state.sortByOrderSelected,
                onApplySortBy = onApplySortBy
            )

            HorizontalDivider()

            ChipSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.vertical
                    ),
                title = stringResource(R.string.discover_availabilities),
                chipItemList = state.availabilitiesList,
                showAllChip = true,
                onSelectionChanged = onAvailabilityListChanged
            )

            HorizontalDivider()

            UserScoreRangeSelector(
                modifier = Modifier
                    .padding(top = Dimens.padding.medium)
                    .padding(horizontal = Dimens.padding.horizontal),
                userScoreMin = state.userScoreRange.start,
                userScoreMax = state.userScoreRange.endInclusive,
                includeNotRated = state.includeNotRated,
                onUserScoreRangeChanged = onUserScoreRangeSelected
            )

            HorizontalDivider()

            ReleaseDatesSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.medium
                    ),
                mediaType = state.mediaType,
                releaseTypeList = state.releaseTypesList,
                releaseDateFrom = state.releaseDateFrom,
                releaseDateTo = state.releaseDateTo,
                isSearchFirstAirDateSelected = state.searchFirstAirDate,
                onSelectReleaseDateRange = onSelectReleaseDateRange,
                onReleaseTypeListChange = onReleaseTypeSelectedListChanged,
                onSearchFirstAirDateChange = onSearchFirstAirDateChange
            )

            HorizontalDivider()

            MinUserVotesSelector(
                modifier = Modifier
                    .padding(top = Dimens.padding.medium)
                    .padding(horizontal = Dimens.padding.horizontal),
                minUserVotes = state.minUserVotes,
                onValueChange = onMinUserVotesChanged
            )

            HorizontalDivider()

            ChipSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.vertical
                    ),
                title = stringResource(R.string.discover_genres),
                chipItemList = state.genreList,
                showAllChip = false,
                onSelectionChanged = onGenreSelectedListChanged
            )

            HorizontalDivider()

            RuntimeSelector(
                modifier = Modifier
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(
                        horizontal = Dimens.padding.horizontal,
                        vertical = Dimens.padding.medium
                    ),
                runtimeStart = state.runtimeRange.start,
                runtimeEnd = state.runtimeRange.endInclusive,
                onValueChanged = onRuntimeRangeSelected
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
fun MediaFilterScreenPreview() {
    AppTheme {
        MediaFilterContent(
            state = MediaFilterState(
                availabilitiesList = MonetizationType.entries.map { it.toSelectableItem(0) },
                releaseTypesList = ReleaseType.entries.map { it.toSelectableItem(0) },
                genreList = MovieGenre.entries.map { it.toSelectableItem(0) },
            ),
            onBackClick = {},
            onApplySortBy = { _, _ -> },
            onAvailabilityListChanged = {},
            onReleaseTypeSelectedListChanged = {},
            onSelectReleaseDateRange = { _, _ -> },
            onGenreSelectedListChanged = {},
            onUserScoreRangeSelected = { _, _, _ -> },
            onMinUserVotesChanged = {},
            onRuntimeRangeSelected = { _, _ -> },
            onApplyFilterClick = {},
            onSearchFirstAirDateChange = {}
        )
    }
}