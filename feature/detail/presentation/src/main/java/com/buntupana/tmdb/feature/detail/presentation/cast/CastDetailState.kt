package com.buntupana.tmdb.feature.detail.presentation.cast

import androidx.compose.ui.graphics.Color
import com.buntupana.tmdb.core.domain.entity.MediaType
import com.buntupana.tmdb.feature.detail.domain.model.Person

data class CastDetailState(
    val isLoading: Boolean = false,
    val isGetContentError: Boolean = false,
    val mediaId: Long,
    val mediaType: MediaType,
    val mediaName: String,
    val posterUrl: String?,
    val releaseYear: String?,
    val backgroundColor: Color,
    val personCastList: List<Person.Cast>? = null,
    val personCrewMap: Map<String, List<Person.Crew>>? = null
)