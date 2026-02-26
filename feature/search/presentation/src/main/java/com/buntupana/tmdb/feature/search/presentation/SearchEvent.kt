package com.buntupana.tmdb.feature.search.presentation

sealed class SearchEvent {

    data class OnSearchSuggestions(val searchKey: String) : SearchEvent()

    data class OnSearch(val searchKey: String, val searchType: SearchType) : SearchEvent()

    data class ChangePage(val page: Int): SearchEvent()

    data object DismissSuggestions : SearchEvent()
}
