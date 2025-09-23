package com.buntupana.tmdb.feature.lists.presentation.create_update_list

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
import com.buntupana.tmdb.feature.lists.domain.usecase.CreateListUseCase
import com.buntupana.tmdb.feature.lists.domain.usecase.UpdateListUseCase
import com.buntupana.tmdb.feature.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class CreateUpdateListViewModel(
    savedStateHandle: SavedStateHandle,
    private val createListUseCase: CreateListUseCase,
    private val updateListUseCase: UpdateListUseCase
) : ViewModel() {

    private val navArgs = savedStateHandle.navArgs<CreateUpdateListRoute>()

    var state by mutableStateOf(
        CreateUpdateListState(
            isNewList = navArgs.listId == null,
            listName = navArgs.listName,
            description = navArgs.listDescription,
            isPublic = navArgs.isPublic
        )
    )
        private set

    private var _sideEffect = Channel<CreateUpdateListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: CreateUpdateListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                CreateUpdateListEvent.CreateUpdateList -> {
                    if (state.isNewList) {
                        createList()
                    } else {
                        updateList()
                    }
                }

                is CreateUpdateListEvent.UpdateForm -> updateForm(
                    listName = event.listName,
                    description = event.description,
                    isPublic = event.isPublic
                )
            }
        }
    }

    private fun updateForm(listName: String, description: String, isPublic: Boolean) {
        state = state.copy(listName = listName, description = description, isPublic = isPublic)
    }

    private suspend fun createList() {

        state = state.copy(isLoading = true)

        createListUseCase(
            name = state.listName,
            description = state.description,
            isPublic = state.isPublic
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    UiText.StringResource(R.string.lists_error_create_list)
                )
            )
        }.onSuccess {
            state = state.copy(isLoading = false)
            _sideEffect.send(CreateUpdateListSideEffect.CreateUpdateListSuccess)
        }
    }

    private suspend fun updateList() {
        state = state.copy(isLoading = true)

        updateListUseCase(
            listId = navArgs.listId ?: 0,
            name = state.listName,
            description = state.description,
            isPublic = state.isPublic
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    UiText.StringResource(R.string.lists_error_create_list)
                )
            )
        }.onSuccess {
            state = state.copy(isLoading = false)
            _sideEffect.send(CreateUpdateListSideEffect.CreateUpdateListSuccess)
        }
    }
}