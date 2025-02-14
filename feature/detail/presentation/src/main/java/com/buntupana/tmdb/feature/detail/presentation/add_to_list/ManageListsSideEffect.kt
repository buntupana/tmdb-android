package com.buntupana.tmdb.feature.detail.presentation.add_to_list

sealed class ManageListsSideEffect {

    data object SetListsSuccess: ManageListsSideEffect()

    data object Dismiss: ManageListsSideEffect()
}