package com.buntupana.tmdb.feature.lists.presentation.create_update_list

sealed class CreateListSideEffect {

    data object CreateListSuccess : CreateListSideEffect()
}