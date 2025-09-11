package com.buntupana.tmdb.feature.detail.presentation.cast

import com.buntupana.tmdb.feature.detail.domain.model.Person
import com.panabuntu.tmdb.core.common.entity.MediaType

data class CastDetailState(
    val isLoading: Boolean = true,
    val isGetContentError: Boolean = false,
    val mediaId: Long,
    val mediaType: MediaType,
    val mediaName: String,
    val posterUrl: String?,
    val releaseYear: String?,
    val backgroundColor: Int?,
    val personCastList: List<Person.Cast>? = null,
    val personCrewMap: Map<String, List<Person.Crew>>? = null
)