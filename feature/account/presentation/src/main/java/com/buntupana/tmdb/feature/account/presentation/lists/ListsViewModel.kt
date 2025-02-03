package com.buntupana.tmdb.feature.account.presentation.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.buntupana.tmdb.feature.account.domain.usecase.GetListTotalCountUseCase
import com.buntupana.tmdb.feature.account.domain.usecase.GetListsPagingUseCase
import com.panabuntu.tmdb.core.common.entity.onError
import com.panabuntu.tmdb.core.common.entity.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getListsPagingUseCase: GetListsPagingUseCase,
    private val getListsTotalCountUseCase: GetListTotalCountUseCase
) : ViewModel() {

    var state by mutableStateOf(ListsState())
        private set

    init {
        onEvent(ListsEvent.GetLists)
    }

    fun onEvent(event: ListsEvent) {
        Timber.d("onEvent() called with: event = [$event]")
        viewModelScope.launch {
            when (event) {
                ListsEvent.GetLists -> getListTotalCount()
            }
        }
    }

    private suspend fun getListTotalCount() {

        state = state.copy(isLoading = true, isError = false)

        getListsTotalCountUseCase()
            .onError {
                state = state.copy(isError = true, isLoading = false)
            }
            .onSuccess { totalCount ->
                state = state.copy(listItemTotalCount = totalCount, isLoading = false)
                getLists()
            }
    }

    private suspend fun getLists() {
        getListsPagingUseCase().let {
            state = state.copy(listItems = it.cachedIn(viewModelScope))
        }
    }
}