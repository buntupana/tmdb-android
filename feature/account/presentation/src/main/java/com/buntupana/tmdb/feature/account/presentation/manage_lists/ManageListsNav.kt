package com.buntupana.tmdb.feature.account.presentation.manage_lists

import com.buntupana.tmdb.core.ui.navigation.Routes
import com.panabuntu.tmdb.core.common.entity.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class ManageListsNav(
    val mediaId: Long,
    val mediaType: MediaType
) : Routes