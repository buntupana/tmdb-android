package com.buntupana.tmdb.feature.lists.presentation.manage_lists

sealed class ManageListsSideEffect {

    data object SetListsSuccess: ManageListsSideEffect()
}