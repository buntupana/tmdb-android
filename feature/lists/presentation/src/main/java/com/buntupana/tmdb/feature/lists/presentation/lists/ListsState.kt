package com.buntupana.tmdb.feature.lists.presentation.lists

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.lists.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

data class ListsState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val listItemTotalCount: Int? = null,
    val listItems: Flow<PagingData<ListItem>>? = null
)