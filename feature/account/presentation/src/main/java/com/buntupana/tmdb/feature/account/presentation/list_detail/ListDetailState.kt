package com.buntupana.tmdb.feature.account.presentation.list_detail

import androidx.paging.PagingData
import com.panabuntu.tmdb.core.common.model.MediaItem
import kotlinx.coroutines.flow.Flow

data class ListDetailState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val listId: Long,
    val listName: String = "",
    val description: String? = null,
    val backdropUrl: String? = null,
    val isPublic: Boolean = true,
    val itemTotalCount: Int? = null,
    val mediaItemList: Flow<PagingData<MediaItem>>? = null
)