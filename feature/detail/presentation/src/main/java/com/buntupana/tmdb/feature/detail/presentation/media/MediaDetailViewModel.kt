package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowDetailsUseCase
import com.buntupana.tmdb.feature.detail.presentation.R
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.SetMediaWatchListUseCase
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import com.panabuntu.tmdb.core.common.util.applyDelayFor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

// to smooth transitions a delay it's applied
private const val FIRST_LOAD_DELAY = 300L

class MediaDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    private val setMediaFavoriteUseCase: SetMediaFavoriteUseCase,
    private val setMediaWatchListUseCase: SetMediaWatchListUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val navArgs: MediaDetailNav = savedStateHandle.navArgs()

    var state by mutableStateOf(
        MediaDetailState(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType,
            backgroundColor = navArgs.backgroundColor
        )
    )
        private set

    init {
        onEvent(MediaDetailEvent.GetMediaDetails)
        viewModelScope.launch {
            sessionManager.session.collectLatest { session ->
                state = state.copy(isUserLoggedIn = session.isLogged)
            }
        }
    }

    fun onEvent(event: MediaDetailEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                MediaDetailEvent.GetMediaDetails -> {
                    when (navArgs.mediaType) {
                        MediaType.MOVIE -> getMovieDetails()
                        MediaType.TV_SHOW -> getTvShowDetails()
                    }
                }

                MediaDetailEvent.SetFavorite -> {
                    setFavorite()
                }

                MediaDetailEvent.SetWatchList -> {
                    setWatchList()
                }

                is MediaDetailEvent.OnRatingSuccess -> {
                    onRatingSuccess(event.rating)
                }
            }
        }
    }

    private suspend fun getMovieDetails() {

        val isLoading = state.mediaDetails == null

        state = state.copy(isLoading = isLoading, isGetContentError = false)

        val startRequestMillis = System.currentTimeMillis()
        getMovieDetailsUseCase(navArgs.mediaId).collectLatest { result ->
            result.onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }.onSuccess {
                if (state.mediaDetails == null) {
                    applyDelayFor(initMillis = startRequestMillis, minDurationDifference = FIRST_LOAD_DELAY)
                }
                state =
                    state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
        }
    }

    private suspend fun getTvShowDetails() {

        val isLoading = state.mediaDetails == null

        state = state.copy(isLoading = isLoading, isGetContentError = false)

        val startRequestMillis = System.currentTimeMillis()
        getTvShowDetailsUseCase(navArgs.mediaId).collectLatest { result ->
            result.onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }.onSuccess {
                if (state.mediaDetails == null) {
                    applyDelayFor(initMillis = startRequestMillis, minDurationDifference = FIRST_LOAD_DELAY)
                }
                state =
                    state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
        }
    }

    private suspend fun setFavorite() {

        if (state.mediaDetails == null || state.isFavoriteLoading) return

        state = state.copy(isFavoriteLoading = true)

        setFavoriteInternal(
            isFavorite = state.mediaDetails?.isFavorite?.not() ?: false,
            isFavoriteLoading = true
        )

        setMediaFavoriteUseCase(
            mediaType = state.mediaDetails?.mediaType!!,
            mediaId = state.mediaDetails?.id!!,
            isFavorite = state.mediaDetails?.isFavorite!!
        ).onError {
            setFavoriteInternal(
                isFavorite = state.mediaDetails?.isFavorite?.not() ?: false,
                isFavoriteLoading = false
            )
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_set_favorite_error
                    )
                )
            )
        }.onSuccess {
            state = state.copy(isFavoriteLoading = false)
        }
    }

    private suspend fun setWatchList() {

        if (state.mediaDetails == null || state.isWatchlistLoading) return

        setWatchlistInternal(
            isWatchlist = state.mediaDetails?.isWatchlisted?.not() ?: false,
            isWatchlistLoading = true
        )

        setMediaWatchListUseCase(
            mediaType = state.mediaDetails?.mediaType!!,
            mediaId = state.mediaDetails?.id!!,
            watchlist = state.mediaDetails?.isWatchlisted!!
        ).onError {
            setWatchlistInternal(
                isWatchlist = state.mediaDetails?.isWatchlisted?.not() ?: false,
                isWatchlistLoading = false
            )
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_set_watchlist_error
                    )
                )
            )
        }.onSuccess {
            state = state.copy(isWatchlistLoading = false)
        }
    }

    private fun onRatingSuccess(rating: Int?) {

        val isWatchListed = when {
            rating != null && state.mediaDetails?.isWatchlisted == true -> false
            else -> state.mediaDetails?.isWatchlisted ?: false
        }

        when (val mediaDetails = state.mediaDetails!!) {
            is MediaDetails.Movie -> mediaDetails.copy(
                userRating = rating,
                isWatchlisted = isWatchListed
            )

            is MediaDetails.TvShow -> mediaDetails.copy(
                userRating = rating,
                isWatchlisted = isWatchListed
            )
        }.let {
            state = state.copy(mediaDetails = it)
        }
    }

    private fun setWatchlistInternal(isWatchlist: Boolean, isWatchlistLoading: Boolean) {
        when (val mediaDetails = state.mediaDetails!!) {
            is MediaDetails.Movie -> mediaDetails.copy(isWatchlisted = isWatchlist)
            is MediaDetails.TvShow -> mediaDetails.copy(isWatchlisted = isWatchlist)
        }.let {
            state = state.copy(
                mediaDetails = it,
                isWatchlistLoading = isWatchlistLoading
            )
        }
    }

    private fun setFavoriteInternal(isFavorite: Boolean, isFavoriteLoading: Boolean) {

        state.mediaDetails ?: return

        when (val mediaDetails = state.mediaDetails!!) {
            is MediaDetails.Movie -> mediaDetails.copy(isFavorite = isFavorite)
            is MediaDetails.TvShow -> mediaDetails.copy(isFavorite = isFavorite)
        }.let {
            state = state.copy(
                mediaDetails = it,
                isFavoriteLoading = isFavoriteLoading
            )
        }
    }
}