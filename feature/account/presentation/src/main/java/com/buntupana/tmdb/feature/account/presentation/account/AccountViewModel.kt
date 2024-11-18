package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panabuntu.tmdb.core.common.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    sessionManager: SessionManager
) : ViewModel() {

    private val session = sessionManager.session

    var state by mutableStateOf(AccountState(isUserLogged = session.value.isLogged))

    init {
        viewModelScope.launch {
            session.collectLatest {
                state = state.copy(
                    isUserLogged = it.isLogged,
                    username = it.accountDetails?.username.orEmpty(),
                    avatarUrl = it.accountDetails?.avatarUrl
                )
            }
        }
    }

    fun onEvent(event: AccountEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
//            when (event) {
//            }
        }
    }
}