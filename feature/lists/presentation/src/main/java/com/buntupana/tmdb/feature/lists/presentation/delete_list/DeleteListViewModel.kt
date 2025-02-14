package com.buntupana.tmdb.feature.lists.presentation.delete_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.lists.domain.usecase.DeleteListUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DeleteListViewModel @Inject constructor(
    private val deleteListUseCase: DeleteListUseCase
) : ViewModel() {

    var state by mutableStateOf(DeleteListState())
        private set

    private var _sideEffect = Channel<DeleteListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private lateinit var navArgs: DeleteListNav

    fun onEvent(event: DeleteListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                DeleteListEvent.ConfirmDeleteList -> deleteList()
                is DeleteListEvent.Init -> {
                    navArgs = event.deleteListNav
                    state = state.copy(listName = navArgs.listName)
                }
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
                        message = UiText.StringResource(R.string.message_error_delete_list)
                    )
                )
            }.onSuccess {
                _sideEffect.send(DeleteListSideEffect.DeleteListSuccess)
            }
    }
}