package com.buntupana.tmdb.feature.account.presentation.manage_lists

sealed class ManageListsSideEffect {

    data object SetListsSuccess: ManageListsSideEffect()

    data object Dismiss: ManageListsSideEffect()
}