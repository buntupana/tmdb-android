package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.MovieGenre
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.TvShowGenre
import com.buntupana.tmdb.feature.discover.presentation.mapper.toSelectableItem
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaFilterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val navArgs: MediaFilterNav = savedStateHandle.navArgs(MediaFilterNav.typeMap)

    var state by mutableStateOf(MediaFilterState())
        private set

    private val _sideEffect = Channel<MediaFilterSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        setupFilters(navArgs.mediaListFilter)
    }

    fun onEvent(event: MediaFilterEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {

                is MediaFilterEvent.ChangeSortBy -> {
                    changeSortBy(sortBySimple = event.sortBySimple, sortByOrder = event.sortByOrder)
                }

                is MediaFilterEvent.SelectMonetizationType -> {
                    state = state.copy(availabilitiesList = event.monetizationTypeList)
                }

                is MediaFilterEvent.SelectReleaseType -> {
                    state = state.copy(releaseTypesList = event.releaseTypeList)
                }

                is MediaFilterEvent.SelectReleaseDateRange -> {
                    state = state.copy(
                        releaseDateFrom = event.releaseDateFrom,
                        releaseDateTo = event.releaseDateTo,
                    )
                }

                is MediaFilterEvent.SelectSearchFirstAirDate -> {
                    state = state.copy(searchFirstAirDate = event.searchFirstAirDate)
                }

                is MediaFilterEvent.SelectGenreNew -> {
                    state = state.copy(genreList = event.genreList)
                }

                is MediaFilterEvent.SelectUserScoreRange -> {
                    state = state.copy(
                        userScoreRange = IntRange(event.min, event.max),
                        includeNotRated = event.includeNotRated
                    )
                }

                is MediaFilterEvent.SelectMinUserVotes -> {
                    state = state.copy(minUserVotes = event.minUserVotes)
                }

                is MediaFilterEvent.SelectRuntimeRange -> {
                    state = state.copy(runtimeRange = IntRange(event.min, event.max))
                }

                MediaFilterEvent.ApplyFilter -> applyFilters()
            }
        }
    }

    private fun setupFilters(mediaListFilter: MediaListFilter) {

        val mediaType: MediaType
        val releaseTypeList: List<SelectableItem>
        val genreList: List<SelectableItem>
        val searchFirstAirDate: Boolean

        when (mediaListFilter) {
            is MediaListFilter.Movie -> {
                mediaType = MediaType.MOVIE
                releaseTypeList = ReleaseType.entries.mapIndexed { index, releaseType ->
                    releaseType.toSelectableItem(
                        id = index,
                        isSelected = mediaListFilter.releaseTypeList.contains(releaseType)
                    )
                }
                genreList = MovieGenre.entries.mapIndexed { index, genre ->
                    genre.toSelectableItem(
                        id = index,
                        isSelected = mediaListFilter.genreList.contains(genre)
                    )
                }
                searchFirstAirDate = false
            }

            is MediaListFilter.TvShow -> {
                mediaType = MediaType.TV_SHOW
                releaseTypeList = emptyList()
                genreList = TvShowGenre.entries.mapIndexed { index, genre ->
                    genre.toSelectableItem(
                        id = index,
                        isSelected = mediaListFilter.genreList.contains(genre)
                    )
                }
                searchFirstAirDate = mediaListFilter.searchFirstAirDate
            }
        }

        state = state.copy(
            mediaType = mediaType,
            sortBySelected = mediaListFilter.sortBy,
            sortByOrderSelected = mediaListFilter.sortByOrder,
            releaseDateFrom = mediaListFilter.releaseDateFrom,
            releaseDateTo = mediaListFilter.releaseDateTo,
            searchFirstAirDate = searchFirstAirDate,
            releaseTypesList = releaseTypeList,
            availabilitiesList = MonetizationType.entries.mapIndexed { index, monetizationType ->
                monetizationType.toSelectableItem(
                    id = index,
                    isSelected = mediaListFilter.monetizationTypeList.contains(monetizationType)
                )
            },
            genreList = genreList,
            userScoreRange = IntRange(mediaListFilter.userScoreMin, mediaListFilter.userScoreMax),
            includeNotRated = mediaListFilter.includeNotRated,
            minUserVotes = mediaListFilter.minUserVotes,
            runtimeRange = IntRange(mediaListFilter.runtimeMin, mediaListFilter.runtimeMax)
        )
    }

    private suspend fun applyFilters() {

        val monetizationTypeList = state.availabilitiesList
            .filter { it.isSelected }
            .map { selectableItem -> MonetizationType.entries[selectableItem.id] }

        val newFilter = when (state.mediaType) {
            MediaType.MOVIE -> {

                val releaseTypeList = state.releaseTypesList
                    .filter { it.isSelected }
                    .map { selectableItem -> ReleaseType.entries[selectableItem.id] }

                val movieGenreList = state.genreList
                    .filter { it.isSelected }
                    .map { selectableItem -> MovieGenre.entries[selectableItem.id] }

                MediaListFilter.Movie(
                    _sortBy = state.sortBySelected,
                    _sortByOrder = state.sortByOrderSelected,
                    _releaseDateFrom = state.releaseDateFrom,
                    _releaseDateTo = state.releaseDateTo,
                    releaseTypeList = releaseTypeList,
                    _monetizationTypeList = monetizationTypeList,
                    genreList = movieGenreList,
                    _userScoreMin = state.userScoreRange.first,
                    _userScoreMax = state.userScoreRange.last,
                    _includeNotRated = state.includeNotRated,
                    _minUserVotes = state.minUserVotes,
                    _runtimeMin = state.runtimeRange.first,
                    _runtimeMax = state.runtimeRange.last,
                )
            }

            MediaType.TV_SHOW -> {
                val tvShowGenreList = state.genreList
                    .filter { it.isSelected }
                    .map { selectableItem -> TvShowGenre.entries[selectableItem.id] }

                MediaListFilter.TvShow(
                    _sortBy = state.sortBySelected,
                    _sortByOrder = state.sortByOrderSelected,
                    airDateFrom = state.releaseDateFrom,
                    airDateTo = state.releaseDateTo,
                    searchFirstAirDate = state.searchFirstAirDate,
                    _monetizationTypeList = monetizationTypeList,
                    genreList = tvShowGenreList,
                    _userScoreMin = state.userScoreRange.first,
                    _userScoreMax = state.userScoreRange.last,
                    _includeNotRated = state.includeNotRated,
                    _minUserVotes = state.minUserVotes,
                    _runtimeMin = state.runtimeRange.first,
                    _runtimeMax = state.runtimeRange.last,
                )
            }
        }
        _sideEffect.send(MediaFilterSideEffect.ApplyFilters(newFilter))
    }

    private fun changeSortBy(sortBySimple: SortBySimple, sortByOrder: SortByOrder) {
        state = state.copy(sortBySelected = sortBySimple, sortByOrderSelected = sortByOrder)
    }
}