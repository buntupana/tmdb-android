package com.buntupana.tmdb.feature.detail.presentation.add_to_list

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class ManageListsNav(
    val mediaId: Long,
    val mediaType: MediaType
) : Routes