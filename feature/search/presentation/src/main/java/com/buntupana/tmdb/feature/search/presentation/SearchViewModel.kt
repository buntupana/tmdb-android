package com.buntupana.tmdb.feature.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.core.ui.util.TYPING_DELAY
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchMediaUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchMoviesUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchPersonsUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchResultCountUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetSearchTvShowsUseCase
import com.buntupana.tmdb.feature.search.domain.usecase.GetTrendingMediaUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getTrendingMediaUseCase: GetTrendingMediaUseCase,
    private val getSearchMediaUseCase: GetSearchMediaUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getSearchTvShowsUseCase: GetSearchTvShowsUseCase,
    private val getSearchPersonsUseCase: GetSearchPersonsUseCase,
    private val getSearchResultCountUseCase: GetSearchResultCountUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private var getTrendingJob: Job? = null
    private var searchSuggestionsJob: Job? = null
    private var searchMoviesJob: Job? = null
    private var searchTvShowsJob: Job? = null
    private var searchPersonsJob: Job? = null
    private var getSearchResultCountJob: Job? = null

    init {
        getTrendingList()
    }

    fun onEvent(event: SearchEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        when (event) {
            is SearchEvent.OnSearchSuggestions -> searchSuggestions(event.searchKey)
            is SearchEvent.OnSearch -> {
                if (event.searchKey.isBlank()) {
                    return
                }
                state = state.copy(
                    searchKey = event.searchKey,
                    isSearchSuggestionsLoading = false,
                    searchSuggestionList = null,
                    isSearchSuggestionsError = false,
                    defaultPage = SearchType.entries.indexOf(event.searchType)
                )
                searchMovies(event.searchKey)
                searchTvShows(event.searchKey)
                searchPersons(event.searchKey)
                getSearchResultCount(event.searchKey)
            }

            SearchEvent.DismissSuggestions -> {
                state = state.copy(
                    isSearchSuggestionsLoading = false,
                    searchSuggestionList = null,
                    isSearchSuggestionsError = false
                )
            }
        }
    }

    private fun getTrendingList() {
        getTrendingJob?.cancel()
        getTrendingJob = viewModelScope.launch {
            getTrendingMediaUseCase()
                .onSuccess { mediaItemList ->
                    state = state.copy(trendingList = mediaItemList.take(10))
                }
        }
    }

    private fun searchSuggestions(searchKey: String) {
        searchSuggestionsJob?.cancel()
        searchSuggestionsJob = viewModelScope.launch {

            if (searchKey.isBlank()) {
                state = state.copy(
                    searchKey = searchKey,
                    isSearchSuggestionsLoading = false,
                    searchSuggestionList = null
                )
                return@launch
            }

            state = state.copy(searchKey = searchKey)
            delay(TYPING_DELAY)

            state = state.copy(
                isSearchSuggestionsLoading = true,
                isSearchSuggestionsError = false
            )
            getSearchMediaUseCase(searchKey)
                .onError {
                    state = state.copy(
                        isSearchSuggestionsLoading = false,
                        isSearchSuggestionsError = true,
                        searchSuggestionList = null
                    )
                }
                .onSuccess {
                    state =
                        state.copy(
                            searchSuggestionList = it.take(13),
                            isSearchSuggestionsLoading = false,
                            isSearchSuggestionsError = false
                        )
                }
        }
    }

    private fun searchMovies(searchKey: String) {
        searchMoviesJob?.cancel()
        searchMoviesJob = viewModelScope.launch {
            getSearchMoviesUseCase(searchKey).let {
                state = state.copy(movieItems = it.cachedIn(viewModelScope))
            }
        }
    }

    private fun searchTvShows(searchKey: String) {
        searchTvShowsJob?.cancel()
        searchTvShowsJob = viewModelScope.launch {
            getSearchTvShowsUseCase(searchKey).let {
                state = state.copy(tvShowItems = it.cachedIn(viewModelScope))
            }
        }
    }

    private fun searchPersons(searchKey: String) {
        searchPersonsJob?.cancel()
        searchPersonsJob = viewModelScope.launch {
            getSearchPersonsUseCase(searchKey).let {
                state = state.copy(personItems = it.cachedIn(viewModelScope))
            }
        }
    }

    private fun getSearchResultCount(searchKey: String) {
        getSearchResultCountJob?.cancel()
        getSearchResultCountJob = viewModelScope.launch {
            state = state.copy(isSearchLoading = true, isSearchError = false)
            getSearchResultCountUseCase(searchKey)
                .onError {
                    state = state.copy(isSearchLoading = false, isSearchError = true)
                }
                .onSuccess { result ->

                    val mediaResultCountList = mutableListOf<MediaResultCount>()

                    MediaResultCount(
                        SearchType.MOVIE,
                        result.moviesCount
                    ).let {
                        mediaResultCountList.add(it)
                    }

                    MediaResultCount(
                        SearchType.TV_SHOW,
                        result.tvShowsCount
                    ).let {
                        mediaResultCountList.add(it)
                    }

                    MediaResultCount(
                        SearchType.PERSON,
                        result.personsCount
                    ).let {
                        mediaResultCountList.add(it)
                    }
                    state = state.copy(
                        resultCountList = mediaResultCountList,
                        isSearchLoading = false,
                        isSearchError = false
                    )
                }
        }
    }
}