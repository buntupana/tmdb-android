package com.buntupana.tmdb.feature.seer.presentation.request

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
import com.buntupana.tmdb.feature.seer.domain.model.SeerMediaInfo
import com.buntupana.tmdb.feature.seer.domain.usecase.CreateSeerRequestUseCase
import com.buntupana.tmdb.feature.seer.domain.usecase.GetSeerMediaInfoUseCase
import com.buntupana.tmdb.feature.seer.presentation.R
import com.panabuntu.tmdb.core.common.entity.MediaType
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import com.panabuntu.tmdb.core.common.model.SeerrStatus
import com.panabuntu.tmdb.core.common.model.canBeRequested
import com.panabuntu.tmdb.core.common.provider.SeerStatusProvider
import com.panabuntu.tmdb.core.common.util.Const
import com.panabuntu.tmdb.core.common.util.applyDelayFor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.time.Duration.Companion.milliseconds

class SeerrRequestViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMediaInfoUseCase: GetSeerMediaInfoUseCase,
    private val createRequestUseCase: CreateSeerRequestUseCase,
    private val statusProvider: SeerStatusProvider
) : ViewModel() {

    private val navArgs: SeerrRequestRoute = savedStateHandle.navArgs()

    var state by mutableStateOf(
        SeerrRequestState(
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType
        )
    )
        private set

    private val _sideEffect = Channel<SeerrRequestSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        Timber.d("init")
        load(navArgs.mediaId, navArgs.mediaType)
    }

    fun onEvent(event: SeerrRequestEvent) {
        Timber.d("onEvent() called with: event = $event")
        when (event) {
            is SeerrRequestEvent.OnSeasonToggle -> {
                toggleSeason(event.seasonNumber)
            }

            SeerrRequestEvent.OnAllSeasonsToggle -> {
                toggleAllSeasons()
            }

            SeerrRequestEvent.Submit -> submit()
        }
    }

    private fun load(mediaId: Long, mediaType: MediaType) {
        state = state.copy(isLoading = true)

        val currentTime = System.currentTimeMillis()

        viewModelScope.launch {
            getMediaInfoUseCase(mediaType, mediaId)
                .onError {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(
                                com.buntupana.tmdb.core.ui.R.string.common_loading_content_error,
                            )
                        )
                    )
                    _sideEffect.send(SeerrRequestSideEffect.Dismiss)
                }
                .onSuccess { info ->

                    if (state.mediaType == MediaType.MOVIE && info.mediaStatus.canBeRequested().not()) {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = UiText.StringResource(
                                    R.string.seerr_request_movie_not_able,
                                )
                            )
                        )
                        _sideEffect.send(SeerrRequestSideEffect.Dismiss)
                        return@onSuccess
                    }

                    // The delay helps to get a fluent open bottomsheet animation
                    applyDelayFor(
                        initMillis = currentTime,
                        minDurationDifference = Const.ANIM_DURATION.toLong()
                    )

                    val mediaInfo = SeerMediaInfo(
                        info.mediaStatus,
                        info.seasons
                            ?.filter { it.seasonNumber >= 0 }
                            ?.sortedBy { it.seasonNumber }
                            .orEmpty()
                    )

                    delay(1000.milliseconds)
                    state = state.copy(
                        isLoading = false,
                        mediaInfo = mediaInfo,
                        ableToSubmit = isAbleToSubmit()
                    )
                }
        }
    }

    private fun toggleSeason(seasonNumber: Int) {
        val selectedSeasons = if (state.selectedSeasons.contains(seasonNumber)) {
            state.selectedSeasons - seasonNumber
        } else {
            state.selectedSeasons + seasonNumber
        }
        state = state.copy(
            selectedSeasons = selectedSeasons,
            ableToSubmit = selectedSeasons.isNotEmpty()
        )
    }

    private fun toggleAllSeasons() {
        val selectableSeasons = state.mediaInfo?.seasons.orEmpty().filter {
            it.status != SeerrStatus.AVAILABLE && it.status != SeerrStatus.REQUESTED
        }.map { it.seasonNumber }

        val allSelected = selectableSeasons.isNotEmpty() && selectableSeasons.all {
            state.selectedSeasons.contains(it)
        }

        val newSelectedSeasons = if (allSelected) {
            state.selectedSeasons - selectableSeasons.toSet()
        } else {
            state.selectedSeasons + selectableSeasons.toSet()
        }

        state = state.copy(
            selectedSeasons = newSelectedSeasons,
            ableToSubmit = newSelectedSeasons.isNotEmpty()
        )
    }

    private fun submit() {
        if (state.isSubmitting) return
        viewModelScope.launch {
            state = state.copy(isSubmitting = true, ableToSubmit = false)
            val seasons = state.selectedSeasons.sorted().ifEmpty { null }

            createRequestUseCase(MediaType.TV_SHOW, state.mediaId, seasons)
                .onSuccess {
                    state = state.copy(isSubmitting = false, ableToSubmit = isAbleToSubmit())
                    statusProvider.invalidate(MediaType.TV_SHOW, state.mediaId)
                    _sideEffect.send(SeerrRequestSideEffect.Dismiss)
                }
                .onError {
                    state = state.copy(isSubmitting = false, ableToSubmit = isAbleToSubmit())
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = UiText.StringResource(
                                R.string.seerr_request_media_error,
                            )
                        )
                    )
                }
        }
    }

    private fun isAbleToSubmit(): Boolean {
        return when (state.mediaType) {
            MediaType.MOVIE -> state.mediaInfo?.mediaStatus.canBeRequested() && state.isSubmitting.not()
            MediaType.TV_SHOW -> state.selectedSeasons.isNotEmpty() && state.isSubmitting.not()
        }
    }
}
