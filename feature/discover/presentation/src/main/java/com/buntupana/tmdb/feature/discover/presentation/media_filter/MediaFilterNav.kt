package com.buntupana.tmdb.feature.discover.presentation.media_filter

import com.buntupana.tmdb.core.ui.navigation.Route
import com.buntupana.tmdb.core.ui.util.navType
import com.buntupana.tmdb.feature.discover.presentation.model.MediaListFilter
import kotlinx.serialization.Serializable

@Serializable
data class MediaFilterNav(
    val mediaListFilter: MediaListFilter,
): Route {
    companion object {
        val typeMap = mapOf(navType<MediaListFilter>())
    }
}
