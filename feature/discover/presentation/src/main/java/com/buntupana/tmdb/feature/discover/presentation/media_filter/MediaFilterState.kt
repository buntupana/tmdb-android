package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.util.SelectableItem
import java.time.LocalDate

data class MediaFilterState(
    val sortBySelected: SortBySimple = SortBySimple.POPULARITY,
    val sortByOrderSelected: SortByOrder = SortByOrder.DESCENDING,
    val availabilitiesList: List<SelectableItem> = emptyList(),
    val releaseTypesList: List<SelectableItem> = emptyList(),
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val genreList: List<SelectableItem> = emptyList(),
    val minUserScore: Int = 0,
    val maxUserScore: Int = 100,
    val includeNotRated: Boolean = true,
    val minUserVotes: Int = 0
)