package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
import java.time.LocalDate

sealed class MediaFilterEvent {
    data class Init(val mediaListFilter: MediaListFilter) : MediaFilterEvent()

    data class ChangeSortBy(
        val sortBySimple: SortBySimple,
        val sortByOrder: SortByOrder
    ) : MediaFilterEvent()

    data class SelectMonetizationType(val monetizationTypeList: List<SelectableItem>) :
        MediaFilterEvent()

    data class SelectReleaseType(val releaseTypeList: List<SelectableItem>) : MediaFilterEvent()

    data class SelectReleaseDateRange(
        val releaseDateFrom: LocalDate?,
        val releaseDateTo: LocalDate?
    ) : MediaFilterEvent()

    data class SelectGenreNew(val genreList: List<SelectableItem>) : MediaFilterEvent()

    data class SelectUserScoreRange(
        val min: Int,
        val max: Int,
        val includeNotRated: Boolean
    ) : MediaFilterEvent()

    data class SelectMinUserVotes(val minUserVotes: Int) : MediaFilterEvent()

    data class SelectRuntimeRange(val min: Int, val max: Int) : MediaFilterEvent()

    data object ApplyFilter : MediaFilterEvent()
}