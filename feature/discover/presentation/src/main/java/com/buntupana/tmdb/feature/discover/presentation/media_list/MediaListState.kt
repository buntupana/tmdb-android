package com.buntupana.tmdb.feature.discover.presentation.media_list

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MediaFilterListDefault
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.MovieDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.media_list.filters.TvShowDefaultFilter
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow

data class MediaListState(
    val isLoading: Boolean = false,
    val mediaType: MediaType = MediaType.MOVIE,
    val mediaListFilter: MediaListFilter = MediaFilterListDefault.popularMovie,
    val movieItems: Flow<PagingData<MediaItem.Movie>>? = null,
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>>? = null,
    val movieDefaultFilter: MovieDefaultFilter = MovieDefaultFilter.POPULAR,
    val tvShowDefaultFilter: TvShowDefaultFilter = TvShowDefaultFilter.POPULAR,
)