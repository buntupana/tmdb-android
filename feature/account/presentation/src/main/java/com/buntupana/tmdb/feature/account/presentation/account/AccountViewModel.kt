package com.buntupana.tmdb.feature.account.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AccountState())

    fun onEvent(event: AccountEvent) {
        Timber.d("onEvent() called with: event = []")
        viewModelScope.launch {
//            when (event) {
//                AccountEvent.CreateToken -> {}
//            }
        }
    }
}