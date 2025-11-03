package com.buntupana.tmdb.feature.search.presentation

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.search.domain.model.SearchItem
import com.panabuntu.tmdb.core.common.model.MediaItem
import com.panabuntu.tmdb.core.common.model.PersonItem
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchKey: String = "",
    val isSearchSuggestionsLoading: Boolean = false,
    val isSearchLoading: Boolean = false,
    val isSearchError: Boolean = false,
    val isSearchSuggestionsError: Boolean = false,
    val trendingList: List<SearchItem> = emptyList(),
    val searchSuggestionList: List<SearchItem>? = null,
    val movieItems: Flow<PagingData<MediaItem.Movie>>? = null,
    val tvShowItems: Flow<PagingData<MediaItem.TvShow>>? = null,
    val personItems: Flow<PagingData<PersonItem>>? = null,
    val resultCountList: List<MediaResultCount> = emptyList(),
    val defaultPage: Int? = null
)
