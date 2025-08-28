package com.buntupana.tmdb.feature.discover.presentation.media_list

import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter

sealed class MediaListEvent {
    data class FilterMediaList(val mediaListFilter: MediaListFilter) : MediaListEvent()
}