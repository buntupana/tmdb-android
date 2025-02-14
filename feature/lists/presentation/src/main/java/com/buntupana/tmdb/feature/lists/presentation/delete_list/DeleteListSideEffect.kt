package com.buntupana.tmdb.feature.lists.presentation.delete_list

sealed class DeleteListSideEffect {

    data object DeleteListSuccess : DeleteListSideEffect()
}