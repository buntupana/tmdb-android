package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.feature.discover.domain.entity.MediaFilter


sealed class MediaFilterSideEffect {

    data class ApplyFilters(val mediaFilter: MediaFilter) : MediaFilterSideEffect()
}