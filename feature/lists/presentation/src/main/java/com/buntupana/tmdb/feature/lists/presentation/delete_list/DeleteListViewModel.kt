package com.buntupana.tmdb.feature.lists.presentation.delete_list

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
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteListUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class DeleteListViewModel(
    savedStateHandle: SavedStateHandle,
    private val deleteListUseCase: DeleteListUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<DeleteListRoute>()

    var state by mutableStateOf(DeleteListState(listName = navArgs.listName))
        private set

    private var _sideEffect = Channel<DeleteListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: DeleteListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                DeleteListEvent.ConfirmDeleteList -> deleteList()
            }
        }
    }

    private suspend fun deleteList() {

        state = state.copy(isLoading = true)

        deleteListUseCase(navArgs.listId)
            .onError {
                state = state.copy(isLoading = false)
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = UiText.StringResource(R.string.lists_error_delete_list)
                    )
                )
            }.onSuccess {
                _sideEffect.send(DeleteListSideEffect.DeleteListSuccess)
            }
    }
}