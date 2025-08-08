package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.util.SelectableItem
import java.time.LocalDate

data class MediaFilterState(
//    val mediaFilter: MediaFilter,
    val sortBySelected: SortBySimple = SortBySimple.POPULARITY,
    val sortByOrderSelected: SortByOrder = SortByOrder.DESCENDING,
    val availabilitiesList: List<SelectableItem> = emptyList(),
    val releaseTypesList: List<SelectableItem> = emptyList(),
    val releaseDateFrom: LocalDate? = null,
    val releaseDateTo: LocalDate? = null,
    val genreList: List<SelectableItem> = emptyList(),
)