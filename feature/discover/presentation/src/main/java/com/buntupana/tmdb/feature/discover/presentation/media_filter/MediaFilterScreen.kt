package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.buntupana.tmdb.core.ui.composables.widget.ChipSelector
import com.buntupana.tmdb.core.ui.theme.Dimens
import com.buntupana.tmdb.core.ui.theme.PrimaryColor
import com.buntupana.tmdb.core.ui.theme.SecondaryColor
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
import timber.log.Timber
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFilterScreen(
    viewModel: MediaFilterViewModel = hiltViewModel(),
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
    onGenreSelectedListChanged: (genreList: List<SelectableItem>) -> Unit,
    onUserScoreRangeSelected: (min: Int, max: Int, includeNotRated: Boolean) -> Unit,
    onMinUserVotesChanged: (minUserVotes: Int) -> Unit,
    onRuntimeRangeSelected: (min: Int, max: Int) -> Unit,
    onApplyFilterClick: () -> Unit,
) {

    SetSystemBarsColors(
        statusBarColor = PrimaryColor,
        navigationBarColor = PrimaryColor,
    )

    Scaffold(
        topBar = {
            MediaFilterTopBar(
                onSaveClick = {},
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryColor)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(
                            horizontal = Dimens.padding.horizontal,
                            vertical = Dimens.padding.medium
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                    onClick = onApplyFilterClick,
                ) {
                    Text("Apply")
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(vertical = Dimens.padding.vertical)
                .verticalScroll(state = rememberScrollState())
                .padding(paddingValues)
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
                userScoreMin = state.userScoreRange.start,
                userScoreMax = state.userScoreRange.endInclusive,
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
                runtimeStart = state.runtimeRange.start,
                runtimeEnd = state.runtimeRange.endInclusive,
                onValueChanged = onRuntimeRangeSelected
            )
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
        onApplyFilterClick = {}
    )
}