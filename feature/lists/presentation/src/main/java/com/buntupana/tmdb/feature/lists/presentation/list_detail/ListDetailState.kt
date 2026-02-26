package com.buntupana.tmdb.feature.lists.presentation.list_detail

import androidx.paging.PagingData
import com.buntupana.tmdb.core.ui.util.MediaItemRevealedViewEntity
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
    val mediaItemList: Flow<PagingData<MediaItemRevealedViewEntity>>? = null,
    val shareLink: String? = null,
    val isDescriptionExpanded: Boolean = false
)