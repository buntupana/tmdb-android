package com.buntupana.tmdb.feature.account.presentation.account

data class AccountState(
    val isUserLogged: Boolean = false,
    val username: String? = null,
    val avatarUrl: String? = null
)