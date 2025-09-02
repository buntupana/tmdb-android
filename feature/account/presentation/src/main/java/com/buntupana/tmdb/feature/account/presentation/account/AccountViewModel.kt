package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.filter_type.MediaFilter
import com.buntupana.tmdb.core.ui.util.LOADING_DELAY
import com.buntupana.tmdb.feature.account.domain.usecase.GetFavoritesUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetWatchlistUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.GetListsUseCase
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber


class AccountViewModel(
    sessionManager: SessionManager,
    private val getWatchlistUseCase: GetWatchlistUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getListsUseCase: GetListsUseCase
) : ViewModel() {

    private val session = sessionManager.session

    var state by mutableStateOf(AccountState(isUserLogged = session.value.isLogged))
        private set

    private var getWatchlistJob: Job? = null
    private var getFavoritesJob: Job? = null
    private var getListsJob: Job? = null

    init {
        viewModelScope.launch {
            session.collectLatest {
                state = state.copy(
                    isUserLogged = it.isLogged,
                    username = it.accountDetails?.username.orEmpty(),
                    avatarUrl = it.accountDetails?.avatarUrl
                )
            }
        }
        onEvent(AccountEvent.GetWatchlist(state.watchlistFilterSelected))
        onEvent(AccountEvent.GetFavorites(state.favoritesFilterSelected))
        onEvent(AccountEvent.GetLists)
    }

    fun onEvent(event: AccountEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                is AccountEvent.GetWatchlist -> getWatchlist(event.mediaFilter)
                is AccountEvent.GetFavorites -> getFavorites(event.mediaFilter)
                AccountEvent.GetLists -> getLists()
            }
        }
    }

    private fun getWatchlist(mediaFilter: MediaFilter) {

        if (session.value.isLogged.not()) return

        getWatchlistJob?.cancel()

        val mediaType = when (mediaFilter) {
            MediaFilter.MOVIES -> MediaType.MOVIE
            MediaFilter.TV_SHOWS -> MediaType.TV_SHOW
        }

        getWatchlistJob = viewModelScope.launch {

            state = state.copy(
                isWatchlistLoadingError = false,
                watchlistFilterSelected = mediaFilter,
                watchlistMediaItemList = if (mediaFilter == state.watchlistFilterSelected) {
                    state.watchlistMediaItemList
                } else {
                    null
                }
            )

            getWatchlistUseCase(mediaType).collectLatest {
                it.onError {
                    state = state.copy(isWatchlistLoadingError = true)
                }.onSuccess { mediaItemList ->
                    // Fake delay to show loading
                    if (state.watchlistMediaItemList == null) {
                        delay(LOADING_DELAY)
                    }
                    state = state.copy(
                        isWatchlistLoadingError = false,
                        watchlistMediaItemList = mediaItemList
                    )
                }
            }
        }
    }

    private fun getFavorites(mediaFilter: MediaFilter) {

        if (session.value.isLogged.not()) return

        getFavoritesJob?.cancel()

        val mediaType = when (mediaFilter) {
            MediaFilter.MOVIES -> MediaType.MOVIE
            MediaFilter.TV_SHOWS -> MediaType.TV_SHOW
        }

        getFavoritesJob = viewModelScope.launch {

            state = state.copy(
                isFavoritesLoadingError = false,
                favoritesFilterSelected = mediaFilter,
                favoritesMediaItemList = if (mediaFilter == state.favoritesFilterSelected) {
                    state.favoritesMediaItemList
                } else {
                    null
                }
            )

            getFavoritesUseCase(mediaType).collectLatest {
                it.onError {
                    state = state.copy(isFavoritesLoadingError = true)
                }.onSuccess { mediaItemList ->
                    // Fake delay to show loading
                    if (state.favoritesMediaItemList == null) {
                        delay(LOADING_DELAY)
                    }
                    state = state.copy(
                        isFavoritesLoadingError = false,
                        favoritesMediaItemList = mediaItemList
                    )
                }
            }
        }
    }

    private fun getLists() {

        if (session.value.isLogged.not()) return

        getListsJob?.cancel()

        getListsJob = viewModelScope.launch {

            state = state.copy(isListsLoadingError = false)

            getListsUseCase().collectLatest {
                it.onError {
                    state = state.copy(isListsLoadingError = true)
                }.onSuccess { userListDetailsList ->
                    // Fake delay to show loading
                    delay(LOADING_DELAY)
                    state = state.copy(userListDetailsList = userListDetailsList)
                }
            }
        }
    }
}