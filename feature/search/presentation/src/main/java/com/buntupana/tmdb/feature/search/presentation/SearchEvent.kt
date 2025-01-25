package com.buntupana.tmdb.feature.search.presentation

sealed class SearchEvent {
    class OnSearchSuggestions(val searchKey: String) : SearchEvent()
    class OnSearch(val searchKey: String, val searchType: SearchType) : SearchEvent()
    data object DismissSuggestions : SearchEvent()
}
