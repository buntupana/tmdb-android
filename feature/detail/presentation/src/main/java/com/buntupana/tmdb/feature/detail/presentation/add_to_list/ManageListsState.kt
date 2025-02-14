package com.buntupana.tmdb.feature.detail.presentation.add_to_list

import com.buntupana.tmdb.feature.account.domain.model.ListItem
import com.panabuntu.tmdb.core.common.entity.MediaType

data class ManageListsState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val searchKey: String = "",
    val mediaType: MediaType = MediaType.MOVIE,
    val listMediaLists: List<ListItem>? = null,
    val listAllLists: List<ListItem>? = null,
)