package com.buntupana.tmdb.feature.account.presentation.create_list

sealed class CreateListSideEffect {

    data object CreateListSuccess : CreateListSideEffect()
}