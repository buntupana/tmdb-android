package com.buntupana.tmdb.feature.search.presentation

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.PersonItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SearchState(
    val searchKey: String = "",
    val isSearchSuggestionsLoading: Boolean = false,
    val isSearchLoading: Boolean = false,
    val isSearchError: Boolean = false,
    val isSearchSuggestionsError: Boolean = false,
    val trendingList: List<SearchItem> = emptyList(),
    val searchSuggestionList: List<SearchItem>? = null,
    val movieItems: Flow<PagingData<MediaItem.Movie>> = flowOf(PagingData.empty()),
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>> = flowOf(PagingData.empty()),
    val personItems: Flow<PagingData<PersonItem>> = flowOf(PagingData.empty()),
    val resultCountList: List<MediaResultCount> = emptyList(),
    val defaultPage: Int = SearchType.entries.indexOf(SearchType.MOVIE)
)
