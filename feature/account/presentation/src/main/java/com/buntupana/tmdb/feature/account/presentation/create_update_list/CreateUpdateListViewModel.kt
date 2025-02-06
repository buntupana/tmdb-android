package com.buntupana.tmdb.feature.account.presentation.create_update_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.account.domain.usecase.CreateListUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.UpdateListUseCase
import com.buntupana.tmdb.feature.account.presentation.R
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateUpdateListViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase,
    private val updateListUseCase: UpdateListUseCase
) : ViewModel() {

    var state by mutableStateOf(CreateListState())
        private set

    private var _sideEffect = Channel<CreateListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var _listId: Long? = null

    fun onEvent(event: CreateListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                CreateListEvent.CreateList -> {
                    if (state.isNewList) {
                        createList()
                    } else {
                        updateList()
                    }
                }
                is CreateListEvent.InitList -> {
                    clearListInfo(
                        listId = event.listId,
                        listName = event.listName,
                        description = event.description.orEmpty(),
                        isPublic = event.isPublic
                    )
                }

                is CreateListEvent.UpdateForm -> updateForm(
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

    private fun clearListInfo(
        listId: Long?,
        listName: String,
        description: String,
        isPublic: Boolean
    ) {
        _listId = listId
        state = state.copy(
            listName = listName,
            description = description,
            isPublic = isPublic,
            isLoading = false,
            isNewList = listId == null
        )
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
                    UiText.StringResource(R.string.message_error_create_list)
                )
            )
        }.onSuccess {
            _sideEffect.send(CreateListSideEffect.CreateListSuccess)
        }
    }

    private suspend fun updateList() {
        state = state.copy(isLoading = true)

        updateListUseCase(
            listId = _listId ?: 0,
            name = state.listName,
            description = state.description,
            isPublic = state.isPublic
        ).onError {
            state = state.copy(isLoading = false)
            SnackbarController.sendEvent(
                SnackbarEvent(
                    UiText.StringResource(R.string.message_error_create_list)
                )
            )
        }.onSuccess {
            _sideEffect.send(CreateListSideEffect.CreateListSuccess)
        }
    }
}