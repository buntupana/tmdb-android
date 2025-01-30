package com.buntupana.tmdb.feature.account.presentation.lists

import androidx.paging.PagingData
import com.buntupana.tmdb.feature.account.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

data class ListsState(
    val isLoading: Boolean = true,
    val listItems: Flow<PagingData<ListItem>>? = null
)