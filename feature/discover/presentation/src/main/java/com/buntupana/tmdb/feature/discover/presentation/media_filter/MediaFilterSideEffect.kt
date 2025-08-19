package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter


sealed class MediaFilterSideEffect {

    data class ApplyFilters(val mediaListFilter: MediaListFilter) : MediaFilterSideEffect()
}