package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter

data class MediaFilterState(
    val mediaFilter: MediaFilter,
    val sortBySelected: SortBySimple = SortBySimple.POPULARITY,
    val sortByOrderSelected: SortByOrder = SortByOrder.DESCENDING,
)