package com.buntupana.tmdb.feature.account.presentation.account.account

import com.buntupana.tmdb.core.ui.filter_type.MediaFilter

sealed class AccountEvent {
    data class GetWatchlist(val mediaFilter: MediaFilter): AccountEvent()
}