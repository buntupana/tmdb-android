package com.buntupana.tmdb.feature.discover.presentation.movies

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.discover.domain.entity.MediaListFilter
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow

data class MoviesState(
    val isLoading: Boolean = false,
    val movieItems: Flow<PagingData<MediaItem.Movie>>? = null,
    val movieFilter: MovieFilter = MovieFilter.POPULAR,
    val mediaListFilter: MediaListFilter = MediaFilterMovieDefault.popular
)