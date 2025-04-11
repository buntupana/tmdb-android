package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import com.panabuntu.tmdb.core.common.entity.MediaType

data class ManageListsState(
    val isContentLoading: Boolean = false,
    val isConfirmLoading: Boolean = false,
    val isError: Boolean = false,
    val searchKey: String = "",
    val mediaType: MediaType,
    val mediaName: String,
    val backgroundColor: Color,
    val posterUrl: String?,
    val releaseYear: String?,
    val userListDetails: List<UserListDetails>? = null,
    val listAllLists: List<UserListDetails>? = null,
)