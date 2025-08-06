package com.buntupana.tmdb.feature.discover.presentation.media_filter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import com.buntupana.tmdb.feature.discover.domain.entity.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaFilterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(
        MediaFilterState(
            mediaFilter = MediaFilter()
        )
    )
        private set

    fun onEvent(event: MediaFilterEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                is MediaFilterEvent.Init -> {
                    setFilters(event.mediaFilter)
                }

                is MediaFilterEvent.ChangeSortBy -> {
                    changeSortBy(sortBySimple = event.sortBySimple, sortByOrder = event.sortByOrder)
                }

                is MediaFilterEvent.SelectMonetizationType -> {
                    val monetizationTypeList =
                        if (state.mediaFilter.monetizationTypeList.contains(event.monetizationType)) {
                            state.mediaFilter.monetizationTypeList.filter { event.monetizationType != it }
                        } else {
                            state.mediaFilter.monetizationTypeList + event.monetizationType
                        }
                    setFilters(state.mediaFilter.copy(monetizationTypeList = monetizationTypeList))
                }

                is MediaFilterEvent.SelectReleaseType -> {
                    val releaseTypeList =
                        if (state.mediaFilter.releaseTypeList.contains(event.releaseType)) {
                            state.mediaFilter.releaseTypeList.filter { event.releaseType != it }
                        } else {
                            state.mediaFilter.releaseTypeList + event.releaseType
                        }
                    setFilters(state.mediaFilter.copy(releaseTypeList = releaseTypeList))
                }

                is MediaFilterEvent.SelectReleaseDateRange -> {
                    setFilters(
                        state.mediaFilter.copy(
                            releaseDateFrom = event.releaseDateFrom,
                            releaseDateTo = event.releaseDateTo
                        )
                    )
                }
            }
        }
    }

    private fun setFilters(mediaFilter: MediaFilter) {

        state = state.copy(mediaFilter = mediaFilter)

        when (mediaFilter.sortBy) {
            SortBy.POPULARITY_DESC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.POPULARITY,
                    sortByOrderSelected = SortByOrder.DESCENDING
                )
            }

            SortBy.POPULARITY_ASC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.POPULARITY,
                    sortByOrderSelected = SortByOrder.ASCENDING
                )
            }

            SortBy.RATING_DESC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.RATING,
                    sortByOrderSelected = SortByOrder.DESCENDING
                )
            }

            SortBy.RATING_ASC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.RATING,
                    sortByOrderSelected = SortByOrder.ASCENDING
                )
            }

            SortBy.RELEASE_DATE_DESC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.RELEASE_DATE,
                    sortByOrderSelected = SortByOrder.DESCENDING
                )
            }

            SortBy.RELEASE_DATE_ASC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.RELEASE_DATE,
                    sortByOrderSelected = SortByOrder.ASCENDING
                )
            }

            SortBy.TITLE_DESC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.TITLE,
                    sortByOrderSelected = SortByOrder.DESCENDING
                )
            }

            SortBy.TITLE_ASC -> {
                state = state.copy(
                    sortBySelected = SortBySimple.TITLE,
                    sortByOrderSelected = SortByOrder.ASCENDING
                )
            }
        }
    }

    private fun changeSortBy(sortBySimple: SortBySimple, sortByOrder: SortByOrder) {
        state = state.copy(sortBySelected = sortBySimple, sortByOrderSelected = sortByOrder)

        state = when (sortBySimple) {
            SortBySimple.POPULARITY -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.POPULARITY_ASC)
                        )
                    }

                    SortByOrder.DESCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.POPULARITY_ASC)
                        )
                    }
                }
            }

            SortBySimple.RATING -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.RATING_ASC)
                        )
                    }

                    SortByOrder.DESCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.RATING_DESC)
                        )
                    }
                }
            }

            SortBySimple.RELEASE_DATE -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.RELEASE_DATE_ASC)
                        )
                    }

                    SortByOrder.DESCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.RELEASE_DATE_DESC)
                        )
                    }
                }
            }

            SortBySimple.TITLE -> {
                when (sortByOrder) {
                    SortByOrder.ASCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.TITLE_ASC)
                        )
                    }

                    SortByOrder.DESCENDING -> {
                        state.copy(
                            mediaFilter = state.mediaFilter.copy(sortBy = SortBy.TITLE_DESC)
                        )
                    }
                }
            }
        }
    }
}