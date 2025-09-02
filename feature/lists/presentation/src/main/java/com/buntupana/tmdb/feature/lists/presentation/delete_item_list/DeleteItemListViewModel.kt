package com.buntupana.tmdb.feature.lists.presentation.delete_item_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteItemListUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class DeleteItemListViewModel(
    private val deleteItemListUseCase: DeleteItemListUseCase
) : ViewModel() {

    var state by mutableStateOf(DeleteItemListState())
        private set

    private var _sideEffect = Channel<DeleteItemListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private lateinit var navArgs: DeleteItemListNav

    fun onEvent(event: DeleteItemListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {

                DeleteItemListEvent.ConfirmDelete -> deleteMediaFromList()

                is DeleteItemListEvent.Init -> {
                    navArgs = event.deleteItemListNav
                    state = state.copy(mediaName = navArgs.mediaName, isLoading = false)
                }
            }
        }
    }

    private suspend fun deleteMediaFromList() {
        state = state.copy(isLoading = true)

        deleteItemListUseCase(
            listId = navArgs.listId,
            mediaId = navArgs.mediaId,
            mediaType = navArgs.mediaType
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    message = UiText.StringResource(
                        R.string.message_error_delete_item_list,
                        navArgs.mediaName
                    )
                )
            )
        }.onSuccess {
            _sideEffect.send(DeleteItemListSideEffect.DeleteSuccess)
        }
    }
}