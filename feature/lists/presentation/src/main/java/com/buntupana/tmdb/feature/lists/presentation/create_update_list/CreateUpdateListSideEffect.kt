package com.buntupana.tmdb.feature.lists.presentation.create_update_list

sealed class CreateUpdateListSideEffect {

    data object CreateUpdateListSuccess : CreateUpdateListSideEffect()
}