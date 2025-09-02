package com.buntupana.tmdb.feature.lists.domain.usecase

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails

data class GetListsFromMediaUseCaseResult(
    val mediaBelongsList: List<UserListDetails>,
    val mediaNotBelongsList: List<UserListDetails>,
    val allListsList: List<UserListDetails>,
)