package com.buntupana.tmdb.feature.account.presentation.create_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.buntupana.tmdb.core.ui.snackbar.SnackbarController
import com.buntupana.tmdb.core.ui.snackbar.SnackbarEvent
import com.buntupana.tmdb.core.ui.util.UiText
import com.buntupana.tmdb.feature.account.domain.usecase.CreateListUseCase
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
class CreateListViewModel @Inject constructor(
    private val createListUseCase: CreateListUseCase
) : ViewModel() {

    var state by mutableStateOf(CreateListState())
        private set

    private var _sideEffect = Channel<CreateListSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onEvent(event: CreateListEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                CreateListEvent.CreateList -> createList()
                CreateListEvent.ClearListInfo -> clearListInfo()
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

    private fun clearListInfo() {
        state = state.copy(
            listName = "",
            description = "",
            isPublic = true,
            isLoading = false
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
}