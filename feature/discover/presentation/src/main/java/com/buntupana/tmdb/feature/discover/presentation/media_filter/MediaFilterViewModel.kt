package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.discover.domain.entity.Genre
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.MonetizationType
import com.buntupana.tmdb.feature.discover.domain.entity.ReleaseType
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import com.buntupana.tmdb.feature.discover.presentation.mapper.toSelectableItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaFilterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(
        MediaFilterState()
    )
        private set

    private val _sideEffect = Channel<MediaFilterSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var mediaFilter: MediaFilter = MediaFilter()

    fun onEvent(event: MediaFilterEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                is MediaFilterEvent.Init -> {
                    mediaFilter = event.mediaFilter
                    setupFilters(event.mediaFilter)
                }

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
                        releaseDateTo = event.releaseDateTo
                    )
                }

                is MediaFilterEvent.SelectGenreNew -> {
                    state = state.copy(genreList = event.genreList)
                }

                MediaFilterEvent.ApplyFilter -> applyFilters()
            }
        }
    }

    private suspend fun applyFilters() {
        val sortBy = when (state.sortBySelected) {
            SortBySimple.POPULARITY -> {
                when (state.sortByOrderSelected) {
                    SortByOrder.ASCENDING -> SortBy.POPULARITY_ASC

                    SortByOrder.DESCENDING -> SortBy.POPULARITY_DESC
                }
            }

            SortBySimple.RATING -> {
                when (state.sortByOrderSelected) {
                    SortByOrder.ASCENDING -> SortBy.RATING_ASC

                    SortByOrder.DESCENDING -> SortBy.RATING_DESC
                }
            }

            SortBySimple.RELEASE_DATE -> {
                when (state.sortByOrderSelected) {
                    SortByOrder.ASCENDING -> SortBy.RELEASE_DATE_ASC

                    SortByOrder.DESCENDING -> SortBy.RELEASE_DATE_DESC
                }
            }

            SortBySimple.TITLE -> {
                when (state.sortByOrderSelected) {
                    SortByOrder.ASCENDING -> SortBy.TITLE_ASC

                    SortByOrder.DESCENDING -> SortBy.TITLE_DESC
                }
            }
        }

        val releaseTypeList = state.releaseTypesList
            .filter { it.isSelected }
            .map { selectableItem -> ReleaseType.entries[selectableItem.id] }

        val monetizationTypeList = state.availabilitiesList
            .filter { it.isSelected }
            .map { selectableItem -> MonetizationType.entries[selectableItem.id] }

        val genreList = state.genreList
            .filter { it.isSelected }
            .map { selectableItem -> Genre.entries[selectableItem.id] }

        val newFilter = mediaFilter.copy(
            sortBy = sortBy,
            releaseDateFrom = state.releaseDateFrom,
            releaseDateTo = state.releaseDateTo,
            releaseTypeList = releaseTypeList,
            monetizationTypeList = monetizationTypeList,
            genreList = genreList
        )

        _sideEffect.send(MediaFilterSideEffect.ApplyFilters(newFilter))
    }

    private fun setupFilters(mediaFilter: MediaFilter) {

        val (sortBy, sortByOrder) = when (mediaFilter.sortBy) {
            SortBy.POPULARITY_DESC -> {
                SortBySimple.POPULARITY to SortByOrder.DESCENDING
            }

            SortBy.POPULARITY_ASC -> {
                SortBySimple.POPULARITY to SortByOrder.ASCENDING
            }

            SortBy.RATING_DESC -> {
                SortBySimple.RATING to SortByOrder.DESCENDING
            }

            SortBy.RATING_ASC -> {
                SortBySimple.RATING to SortByOrder.ASCENDING
            }

            SortBy.RELEASE_DATE_DESC -> {
                SortBySimple.RELEASE_DATE to SortByOrder.DESCENDING
            }

            SortBy.RELEASE_DATE_ASC -> {
                SortBySimple.RELEASE_DATE to SortByOrder.ASCENDING
            }

            SortBy.TITLE_DESC -> {
                SortBySimple.TITLE to SortByOrder.DESCENDING
            }

            SortBy.TITLE_ASC -> {
                SortBySimple.TITLE to SortByOrder.ASCENDING
            }
        }

        state = state.copy(
            sortBySelected = sortBy,
            sortByOrderSelected = sortByOrder,
            releaseDateFrom = mediaFilter.releaseDateFrom,
            releaseDateTo = mediaFilter.releaseDateTo,
            releaseTypesList = ReleaseType.entries.mapIndexed { index, releaseType ->
                releaseType.toSelectableItem(
                    id = index,
                    isSelected = mediaFilter.releaseTypeList.contains(releaseType)
                )
            },
            availabilitiesList = MonetizationType.entries.mapIndexed { index, monetizationType ->
                monetizationType.toSelectableItem(
                    id = index,
                    isSelected = mediaFilter.monetizationTypeList.contains(monetizationType)
                )
            },
            genreList = Genre.entries.mapIndexed { index, genre ->
                genre.toSelectableItem(
                    id = index,
                    isSelected = mediaFilter.genreList.contains(genre)
                )
            }
        )
    }

    private fun changeSortBy(sortBySimple: SortBySimple, sortByOrder: SortByOrder) {
        state = state.copy(sortBySelected = sortBySimple, sortByOrderSelected = sortByOrder)
    }
}