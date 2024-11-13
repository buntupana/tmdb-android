package com.buntupana.tmdb.feature.search.presentation

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchState(
    val searchKey: String = "",
    val isSearchSuggestionsLoading: Boolean = false,
    val isSearchLoading: Boolean = false,
    val isSearchError: Boolean = false,
    val isSearchSuggestionsError: Boolean = false,
    val trendingList: List<com.buntupana.tmdb.feature.search.domain.model.SearchItem> = emptyList(),
    val searchSuggestionList: List<com.buntupana.tmdb.feature.search.domain.model.SearchItem>? = null,
    val movieItems: Flow<PagingData<com.panabuntu.tmdb.core.common.model.MediaItem.Movie>> = flowOf(PagingData.empty()),
    val tvShowItems: Flow<PagingData<com.panabuntu.tmdb.core.common.model.MediaItem.TvShow>> = flowOf(PagingData.empty()),
    val personItems: Flow<PagingData<com.panabuntu.tmdb.core.common.model.PersonItem>> = flowOf(PagingData.empty()),
    val resultCountList: List<MediaResultCount> = emptyList(),
    val defaultSearchType: SearchType = SearchType.MOVIE
)
