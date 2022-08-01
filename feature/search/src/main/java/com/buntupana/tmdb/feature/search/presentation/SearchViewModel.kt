package com.buntupana.tmdb.feature.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.presentation.util.TYPING_DELAY
import com.buntupana.tmdb.feature.search.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getSearchMediaUseCase: GetSearchMediaUseCase,
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getSearchTvShowsUseCase: GetSearchTvShowsUseCase,
    private val getSearchPersonsUseCase: GetSearchPersonsUseCase,
    private val getSearchResultCountUseCase: GetSearchResultCountUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())

    var searchSuggestionsJob: Job? = null
    var searchMoviesJob: Job? = null
    var searchTvShowsJob: Job? = null
    var searchPersonsJob: Job? = null
    var getSearchResultCountJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchSuggestions -> searchMedia(event.searchKey)
            is SearchEvent.OnSearch -> {
                searchMovies(event.searchKey)
                searchTvShows(event.searchKey)
                searchPersons(event.searchKey)
                getSearchResultCount(event.searchKey)
            }
        }
    }

    private fun searchMedia(searchKey: String) {
        searchSuggestionsJob?.cancel()
        searchSuggestionsJob = viewModelScope.launch {
            state = state.copy(searchKey = searchKey)
            delay(TYPING_DELAY)
            getSearchMediaUseCase(searchKey) {
                loading {
                    state = state.copy(isSearchSuggestionsLoading = true)
                }
                error {
                    state = state.copy(isSearchSuggestionsLoading = false)
                }
                success {
                    state = state.copy(isSearchSuggestionsLoading = false)
                }
            }
        }
    }

    private fun searchMovies(searchKey: String) {
        searchMoviesJob?.cancel()
        searchMoviesJob = viewModelScope.launch {
            getSearchMoviesUseCase(searchKey).let {
                state = state.copy(movieItems = it)
            }
        }
    }

    private fun searchTvShows(searchKey: String) {
        searchTvShowsJob?.cancel()
        searchTvShowsJob = viewModelScope.launch {
            getSearchTvShowsUseCase(searchKey).let {
                state = state.copy(tvShowItems = it)
            }
        }
    }

    private fun searchPersons(searchKey: String) {
        searchPersonsJob?.cancel()
        searchPersonsJob = viewModelScope.launch {
            getSearchPersonsUseCase(searchKey).let {
                state = state.copy(personItems = it)
            }
        }
    }

    private fun getSearchResultCount(searchKey: String) {
        getSearchResultCountJob?.cancel()
        getSearchResultCountJob = viewModelScope.launch {
            getSearchResultCountUseCase(searchKey) {
                loading {
                    state = state.copy(isSearchLoading = true)
                }
                error {
                    state = state.copy(isSearchLoading = false)
                }
                success { result ->

                    val mediaResultCountList = mutableListOf<MediaResultCount>()

                    MediaResultCount(
                        com.buntupana.tmdb.core.R.string.text_movies,
                        result.moviesCount
                    ).let {
                        mediaResultCountList.add(it)
                    }

                    MediaResultCount(
                        com.buntupana.tmdb.core.R.string.text_tv_shows,
                        result.tvShowsCount
                    ).let {
                        mediaResultCountList.add(it)
                    }

                    MediaResultCount(
                        com.buntupana.tmdb.core.R.string.text_people,
                        result.personsCount
                    ).let {
                        mediaResultCountList.add(it)
                    }
                    state =
                        state.copy(resultCountList = mediaResultCountList, isSearchLoading = false)
                }
            }
        }
    }
}