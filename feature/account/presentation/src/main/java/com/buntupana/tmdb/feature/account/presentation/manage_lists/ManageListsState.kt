package com.buntupana.tmdb.feature.account.presentation.manage_lists

import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.panabuntu.tmdb.core.common.entity.MediaType

data class ManageListsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchKey: String = "",
    val mediaType: MediaType,
    val listMediaLists: List<ListItem>? = null,
    val listAllLists: List<ListItem>? = null,
)