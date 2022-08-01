package com.buntupana.tmdb.feature.search.presentation

import androidx.paging.PagingData
import com.buntupana.tmdb.core.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchState(
    val searchKey: String = "",
    val isSearchSuggestionsLoading: Boolean = false,
    val isSearchLoading: Boolean = false,
    val movieItems: Flow<PagingData<MediaItem.Movie>> = flowOf(PagingData.empty()),
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>> = flowOf(PagingData.empty()),
    val personItems: Flow<PagingData<MediaItem.Person>> = flowOf(PagingData.empty()),
    val resultCountList: List<MediaResultCount> = emptyList()
)
