package com.buntupana.tmdb.feature.account.presentation.lists

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

data class ListsState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val listItemTotalCount: Int? = 0,
    val listItems: Flow<PagingData<ListItem>>? = null
)