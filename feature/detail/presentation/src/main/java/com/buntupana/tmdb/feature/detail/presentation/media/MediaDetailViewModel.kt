package com.buntupana.tmdb.feature.detail.presentation.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.theme.DetailBackgroundColor
import com.buntupana.tmdb.core.ui.util.navArgs
import com.buntupana.tmdb.feature.account.domain.usecase.SetMediaFavoriteUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.SetMediaWatchListUseCase
import com.buntupana.tmdb.feature.detail.domain.model.MediaDetails
import com.buntupana.tmdb.feature.detail.domain.usecase.GetMovieDetailsUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.GetTvShowDetailsUseCase
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.manager.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaDetailViewModel @Inject constructor(
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
            backgroundColor = Color(navArgs.backgroundColor ?: DetailBackgroundColor.toArgb())
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
                    // delay to gives the server time to update the rating
                    delay(800)
                    onEvent(MediaDetailEvent.GetMediaDetails)
                }
            }
        }
    }

    private suspend fun getMovieDetails() {

        val isLoading = state.mediaDetails == null

        state = state.copy(isLoading = isLoading, isGetContentError = false)

        getMovieDetailsUseCase(navArgs.mediaId)
            .onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }
            .onSuccess {
                state = state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
    }

    private suspend fun getTvShowDetails() {

        val isLoading = state.mediaDetails == null

        state = state.copy(isLoading = isLoading, isGetContentError = false)

        getTvShowDetailsUseCase(navArgs.mediaId)
            .onError {
                state = state.copy(isLoading = false, isGetContentError = true)
            }
            .onSuccess {
                state =
                    state.copy(isLoading = false, isGetContentError = false, mediaDetails = it)
            }
    }

    private suspend fun setFavorite() {

        if (state.mediaDetails == null || state.isFavoriteLoading) return

        state = state.copy(isFavoriteLoading = true)

        setMediaFavoriteUseCase(
            mediaType = state.mediaDetails?.mediaType!!,
            mediaId = state.mediaDetails?.id!!,
            favorite = state.mediaDetails?.isFavorite!!.not()
        ).onError {
            state = state.copy(isFavoriteLoading = false)
        }.onSuccess {
            when (val mediaDetails = state.mediaDetails!!) {
                is MediaDetails.Movie -> mediaDetails.copy(isFavorite = mediaDetails.isFavorite.not())
                is MediaDetails.TvShow -> mediaDetails.copy(isFavorite = mediaDetails.isFavorite.not())
            }.let {
                state = state.copy(
                    mediaDetails = it,
                    isFavoriteLoading = false
                )
            }
        }
    }

    private suspend fun setWatchList() {

        if (state.mediaDetails == null || state.isWatchlistLoading) return

        state = state.copy(isWatchlistLoading = true)

        setMediaWatchListUseCase(
            mediaType = state.mediaDetails?.mediaType!!,
            mediaId = state.mediaDetails?.id!!,
            watchlist = state.mediaDetails?.isWatchlisted!!.not()
        ).onError {
            state = state.copy(isWatchlistLoading = false)
        }.onSuccess {
            when (val mediaDetails = state.mediaDetails!!) {
                is MediaDetails.Movie -> mediaDetails.copy(isWatchlisted = mediaDetails.isWatchlisted.not())
                is MediaDetails.TvShow -> mediaDetails.copy(isWatchlisted = mediaDetails.isWatchlisted.not())
            }.let {
                state = state.copy(
                    mediaDetails = it,
                    isWatchlistLoading = false
                )
            }
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
}