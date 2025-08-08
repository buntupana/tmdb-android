package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter


sealed class MediaFilterSideEffect {

    data class ApplyFilters(val mediaListFilter: MediaListFilter) : MediaFilterSideEffect()
}