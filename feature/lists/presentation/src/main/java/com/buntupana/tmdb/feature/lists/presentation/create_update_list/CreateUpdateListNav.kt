package com.buntupana.tmdb.feature.lists.presentation.create_update_list

import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class CreateUpdateListNav(
    val listId: Long? = null,
    val listName: String = "",
    val listDescription: String = "",
    val isPublic: Boolean = true
): Routes