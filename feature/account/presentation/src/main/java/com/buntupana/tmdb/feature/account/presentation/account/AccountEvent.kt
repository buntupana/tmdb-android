package com.buntupana.tmdb.feature.account.presentation.account

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter

sealed class AccountEvent {
    data class GetWatchlist(val mediaFilter: MediaFilter): AccountEvent()
    data class GetFavorites(val mediaFilter: MediaFilter): AccountEvent()
    data object GetLists: AccountEvent()
}