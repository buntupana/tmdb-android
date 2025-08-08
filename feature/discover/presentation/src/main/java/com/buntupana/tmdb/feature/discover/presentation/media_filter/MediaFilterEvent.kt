package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter
import java.time.LocalDate

sealed class MediaFilterEvent {
    data class Init(val mediaFilter: MediaFilter) : MediaFilterEvent()

    data class ChangeSortBy(
        val sortBySimple: SortBySimple,
        val sortByOrder: SortByOrder
    ) : MediaFilterEvent()

    data class SelectMonetizationType(val monetizationTypeList: List<SelectableItem>) : MediaFilterEvent()

    data class SelectReleaseType(val releaseTypeList: List<SelectableItem>) : MediaFilterEvent()

    data class SelectReleaseDateRange(val releaseDateFrom: LocalDate?, val releaseDateTo: LocalDate?) : MediaFilterEvent()

    data class SelectGenreNew(val genreList: List<SelectableItem>): MediaFilterEvent()

    data object ApplyFilter: MediaFilterEvent()
}