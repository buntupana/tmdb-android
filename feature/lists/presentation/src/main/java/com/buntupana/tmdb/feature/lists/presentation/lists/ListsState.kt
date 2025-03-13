package com.buntupana.tmdb.feature.lists.presentation.lists

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.model.UserListDetails
import kotlinx.coroutines.flow.Flow

data class ListsState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val listItemTotalCount: Int? = null,
    val userListDetailsList: Flow<PagingData<UserListDetails>>? = null
)