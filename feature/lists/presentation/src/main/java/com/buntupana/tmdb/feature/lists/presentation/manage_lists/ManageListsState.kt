package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.panabuntu.tmdb.core.common.entity.MediaType

data class ManageListsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchKey: String = "",
    val mediaType: MediaType,
    val userListDetails: List<UserListDetails>? = null,
    val listAllLists: List<UserListDetails>? = null,
)