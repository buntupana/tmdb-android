package com.buntupana.tmdb.feature.detail.presentation.rating

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
import com.buntupana.tmdb.feature.account.domain.usecase.AddMediaRatingUseCase
import com.buntupana.tmdb.feature.detail.domain.usecase.AddEpisodeRatingUseCase
import com.buntupana.tmdb.feature.detail.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.NetworkError
import com.panabuntu.tmdb.core.common.entity.Result
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class RatingViewModel(
    savedStateHandle: SavedStateHandle,
    private val addMediaRatingUseCase: AddMediaRatingUseCase,
    private val addEpisodeRatingUseCase: AddEpisodeRatingUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<RatingNav>(RatingNav.typeMap)

    var state by mutableStateOf(
        RatingState(
            mediaTitle = navArgs.ratingMediaType.title,
            rating = navArgs.ratingMediaType.rating ?: 0,
            ratingTitle = getRatingTitle(navArgs.ratingMediaType.rating)
        )
    )
        private set

    private val _sideEffect = Channel<RatingSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: RatingEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
            when (event) {
                is RatingEvent.SetRating -> {
                    state = state.copy(
                        rating = event.rating,
                        ratingTitle = getRatingTitle(event.rating)
                    )
                }

                RatingEvent.AddRating -> addRating(rating = state.rating)

                RatingEvent.ClearRating -> addRating(rating = null)
            }
        }
    }
    private suspend fun addRating(rating: Int?) {

        state = state.copy(isLoading = true)

        getAddRatingFunction(rating).invoke()
            .onError {
                state = state.copy(isLoading = false)
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = UiText.StringResource(
                            R.string.message_rating_error,
                            state.mediaTitle
                        )
                    )
                )
            }
            .onSuccess {
                state = state.copy(isLoading = false)
                _sideEffect.send(RatingSideEffect.AddRatingSuccess(rating))
            }
    }


    private fun getRatingTitle(rating: Int?): UiText {

        val ratingTitle = when (rating) {
            10 -> R.string.text_rating_10

            20 -> R.string.text_rating_20

            30 -> R.string.text_rating_30

            40 -> R.string.text_rating_40

            50 -> R.string.text_rating_50

            60 -> R.string.text_rating_60

            70 -> R.string.text_rating_70

            80 -> R.string.text_rating_80

            90 -> R.string.text_rating_90

            100 -> R.string.text_rating_100

            else -> com.buntupana.tmdb.core.ui.R.string.empty
        }

        return UiText.StringResource(ratingTitle)
    }

    private fun getAddRatingFunction(rating: Int?): suspend () -> Result<Unit, NetworkError> {
        return when (navArgs.ratingMediaType) {
            is RatingMediaType.Episode -> {
                {
                    addEpisodeRatingUseCase(
                        tvShowId = navArgs.ratingMediaType.tvShowId,
                        seasonNumber = navArgs.ratingMediaType.seasonNumber,
                        episodeNumber = navArgs.ratingMediaType.episodeNumber,
                        rating = rating
                    )
                }
            }

            is RatingMediaType.Movie -> {
                {
                    addMediaRatingUseCase(
                        mediaType = MediaType.MOVIE,
                        mediaId = navArgs.ratingMediaType.movieId,
                        value = rating
                    )
                }
            }

            is RatingMediaType.TvShow -> {
                {
                    addMediaRatingUseCase(
                        mediaType = MediaType.TV_SHOW,
                        mediaId = navArgs.ratingMediaType.tvShowId,
                        value = rating
                    )
                }
            }
        }
    }
}