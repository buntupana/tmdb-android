package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.util.SelectableItem
import com.panabuntu.tmdb.core.common.entity.MediaType
import java.time.LocalDate

data class MediaFilterState(
    val mediaType: MediaType = MediaType.MOVIE,
    val sortBySelected: SortBySimple = SortBySimple.POPULARITY,
    val sortByOrderSelected: SortByOrder = SortByOrder.DESCENDING,
    val availabilitiesList: List<SelectableItem> = emptyList(),
    val releaseTypesList: List<SelectableItem> = emptyList(),
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val searchFirstAirDate: Boolean = false,
    val genreList: List<SelectableItem> = emptyList(),
    val userScoreRange: IntRange = IntRange(0, 100),
    val includeNotRated: Boolean = true,
    val minUserVotes: Int = 0,
    val runtimeRange: IntRange = IntRange(0, 390),
)