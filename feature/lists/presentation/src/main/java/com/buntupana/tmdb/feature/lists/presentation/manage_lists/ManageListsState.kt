package com.buntupana.tmdb.feature.lists.presentation.manage_lists

import com.buntupana.tmdb.feature.lists.domain.model.MediaList
import com.panabuntu.tmdb.core.common.entity.MediaType

data class ManageListsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchKey: String = "",
    val mediaType: MediaType,
    val listMediaLists: List<MediaList>? = null,
    val listAllLists: List<MediaList>? = null,
)