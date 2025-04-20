package com.buntupana.tmdb.app.presentation

sealed class MainActivityEvent {
    data object ReloadSession: MainActivityEvent()
}