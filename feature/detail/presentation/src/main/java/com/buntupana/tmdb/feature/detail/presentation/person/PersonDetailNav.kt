package com.buntupana.tmdb.feature.detail.presentation.person

import com.buntupana.tmdb.core.ui.navigation.Routes
import kotlinx.serialization.Serializable

@Serializable
data class PersonDetailNav(
    val personId: Long
) : Routes
