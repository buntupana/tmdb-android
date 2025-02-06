package com.buntupana.tmdb.feature.account.presentation.create_update_list

sealed class CreateListSideEffect {

    data object CreateListSuccess : CreateListSideEffect()
}