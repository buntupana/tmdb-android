package com.buntupana.tmdb.feature.detail.presentation.rating

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.detail.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.util.applyDelayFor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class RatingViewModel @Inject constructor(
    private val addMediaRatingUseCase: com.buntupana.tmdb.feature.account.domain.usecase.AddMediaRatingUseCase
) : ViewModel() {

    var state by mutableStateOf(RatingState(mediaTitle = "", rating = 0))
        private set

    private val _sideEffect = Channel<RatingSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var mediaId = 0L
    private var mediaType = MediaType.MOVIE

    fun init(navArgs: RatingNav) {
        state = RatingState(
            mediaTitle = navArgs.mediaTitle,
            rating = navArgs.rating ?: 0
        )
        mediaId = navArgs.mediaId
        mediaType = navArgs.mediaType
    }

    fun onEvent(event: RatingEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
            when (event) {
                is RatingEvent.SetRating -> {
                    state = state.copy(rating = event.rating)
                }

                RatingEvent.AddRating -> addRating(rating = state.rating)
                RatingEvent.ClearRating -> addRating(rating = null)
            }
        }
    }

    private suspend fun addRating(rating: Int?) {

        val initMillis = System.currentTimeMillis()

        state = state.copy(isLoading = true)

        addMediaRatingUseCase(mediaType, mediaId, rating)
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
                applyDelayFor(initMillis)
                _sideEffect.send(RatingSideEffect.AddRatingSuccess(rating))
                // apply delay to end the hide animation and then reset the loading state
                delay(500)
                state = state.copy(isLoading = false)
            }
    }
}