package com.buntupana.tmdb.feature.account.presentation.delete_list

sealed class DeleteListSideEffect {

    data object DeleteListSuccess : DeleteListSideEffect()
}